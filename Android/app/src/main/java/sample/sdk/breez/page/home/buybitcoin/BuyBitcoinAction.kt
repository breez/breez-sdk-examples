package sample.sdk.breez.page.home.buybitcoin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import breez_sdk.BuyBitcoinProvider
import sample.sdk.breez.R
import sample.sdk.breez.page.BreezSdkSampleTheme

@Composable
fun BuyBitcoinAction(
    initialProvider: BuyBitcoinProvider,
    onBuyBitcoinClick: (BuyBitcoinProvider) -> Unit,
) {
    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }
    var selectedProvider by rememberSaveable {
        mutableStateOf(initialProvider)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable { isContextMenuVisible = true },
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = providerLabel(selectedProvider),
            )

            DropdownMenu(
                expanded = isContextMenuVisible,
                onDismissRequest = {
                    isContextMenuVisible = false
                },
                offset = DpOffset(0.dp, 0.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                for (provider in BuyBitcoinProvider.values()) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = providerLabel(provider),
                                modifier = Modifier
                                    .padding(
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 2.dp,
                                        bottom = 2.dp,
                                    )
                                    .fillMaxWidth(),
                            )
                        },
                        onClick = {
                            selectedProvider = provider
                            isContextMenuVisible = false
                        },
                    )
                }
            }
        }

        Button(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 16.dp,
            ),
            onClick = {
                onBuyBitcoinClick(selectedProvider)
            },
        ) {
            Text(stringResource(R.string.buy_bitcoin, providerLabel(selectedProvider)))
        }

    }
}

@Composable
private fun providerLabel(provider: BuyBitcoinProvider) = when (provider) {
    BuyBitcoinProvider.MOONPAY -> stringResource(
        id = R.string.buy_bitcoin_provider_moonpay,
    )
}

@[Composable Preview(
    showBackground = true,
    heightDp = 200,
    widthDp = 300,
)]
fun BuyBitcoinActionPreview() {
    BreezSdkSampleTheme {
        BuyBitcoinAction(BuyBitcoinProvider.MOONPAY) {
        }
    }
}
