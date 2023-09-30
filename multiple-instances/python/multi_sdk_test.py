import breez_sdk
import os

class SDKListener(breez_sdk.EventListener):
   def on_event(self, event):
      print(event)

def mkdir(dir: str):
    try:
        os.mkdir(dir)
    except FileExistsError:
        pass

def get_sdk(breez_sdk_api_key: str,
    working_dir: str,
    invite_code: str,
    mnemonic: str,
) -> breez_sdk.BlockingBreezServices:
    # Create working dir
    full_working_dir = os.path.join(os.getcwd(), working_dir)
    mkdir(full_working_dir)
    # Configure and connect
    seed = breez_sdk.mnemonic_to_seed(mnemonic)
    config = breez_sdk.default_config(breez_sdk.EnvironmentType.PRODUCTION, breez_sdk_api_key, breez_sdk.NodeConfig.GREENLIGHT(breez_sdk.GreenlightNodeConfig(None, invite_code)))
    config.working_dir = full_working_dir
    sdk_services = breez_sdk.connect(config, seed, SDKListener())  
    # Get node info
    node_info = sdk_services.node_info()    
    print(node_info) 
    return sdk_services

def multi_sdk_test():        
    # Set API key
    breez_sdk_api_key = "..."
    # Connect
    sdk_1 = get_sdk(breez_sdk_api_key, 
                    "working-dir-1", 
                    "...", # Invite code for SDK 1
                    "...", # Mnemonic for SDK 1
    )
    sdk_2 = get_sdk(breez_sdk_api_key, 
                    "working-dir-2", 
                    "...", # Invite code for SDK 2
                    "...", # Mnemonic for SDK 2
    )
    # Disconnect
    sdk_1.disconnect()
    sdk_2.disconnect()

multi_sdk_test()
