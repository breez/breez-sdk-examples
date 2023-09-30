use anyhow::Result;
use bip39::{Language, Mnemonic};
use breez_sdk_core::*;
use log::info;
use std::str::FromStr;
use std::sync::Arc;

struct AppEventListener {}
impl EventListener for AppEventListener {
    fn on_event(&self, e: BreezEvent) {
        info!("Received Breez event: {e:?}");
    }
}

/// On first run (if you don't already have a node), set the `invite_code` and leave `mnemonic` as None.
///
/// On subsequent runs, or if you already have a node, set the `mnemonic`. The `invite_code` can be left empty.
async fn get_sdk(
    breez_sdk_api_key: &str,
    working_dir: &str,
    invite_code: Option<&str>,
    mnemonic: Option<&str>,
) -> Result<Arc<BreezServices>> {
    let mnemonic_obj = match mnemonic {
        None => {
            let mnemonic = Mnemonic::generate_in(Language::English, 12)?;
            println!("Generated mnemonic: {mnemonic}");
            mnemonic
        }
        Some(mnemonic_str) => Mnemonic::from_str(mnemonic_str)?,
    };

    let seed = mnemonic_obj.to_seed("");

    let mut config = BreezServices::default_config(
        EnvironmentType::Production,
        breez_sdk_api_key.into(),
        breez_sdk_core::NodeConfig::Greenlight {
            config: GreenlightNodeConfig {
                partner_credentials: None,
                invite_code: invite_code.map(Into::into),
            },
        },
    );
    config.working_dir = working_dir.into();

    // Create working dir if it doesn't exist
    std::fs::create_dir_all(working_dir)?;

    let sdk = BreezServices::connect(config, seed.to_vec(), Box::new(AppEventListener {})).await?;

    Ok(sdk)
}

#[tokio::main]
async fn main() -> Result<()> {
    let log_dir = "sdk-log-dir";
    std::fs::create_dir_all(log_dir)?;
    BreezServices::init_logging(log_dir, None)?;

    let breez_sdk_api_key = "...";

    let sdk_1 = get_sdk(
        breez_sdk_api_key,
        "working-dir-1",
        Some("..."), // Invite code for SDK 1
        Some("..."), // Mnemonic, if known SDK 1
    )
    .await?;

    info!("[sdk_1] Node info: {:#?}", sdk_1.node_info()?);

    let sdk_2 = get_sdk(
        breez_sdk_api_key,
        "working-dir-2", // Separate working dir
        Some("..."),     // Invite code for SDK 2
        Some("..."),     // Mnemonic, if known for SDK 2
    )
    .await?;

    info!("[sdk_2] Node info: {:#?}", sdk_2.node_info()?);

    sdk_1.disconnect().await?;
    sdk_2.disconnect().await?;

    Ok(())
}
