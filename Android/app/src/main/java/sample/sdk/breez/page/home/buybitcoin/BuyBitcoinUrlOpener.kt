package sample.sdk.breez.page.home.buybitcoin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import sample.sdk.breez.R
import sample.sdk.breez.page.BreezSdkSampleTheme

@Composable
fun BuyBitcoinUrlOpener(
    url: String,
    onUrlClick: (String) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = stringResource(R.string.buy_bitcoin_open_url),
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 4.dp,
                )
                .clickable { onUrlClick(url) },
        )

        Text(
            text = url,
            modifier = Modifier
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 4.dp,
                    bottom = 4.dp,
                )
                .clickable { onUrlClick(url) },
        )

        Button(
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 8.dp,
            ),
            onClick = {
                onUrlClick(url)
            },
        ) {
            Text(stringResource(R.string.buy_bitcoin_open_url_action))
        }
    }
}

@[Composable Preview(
    showBackground = true,
    heightDp = 600,
    widthDp = 300,
)]
fun BuyBitcoinUrlOpenerPreview() {
    BreezSdkSampleTheme {
        BuyBitcoinUrlOpener(
            "https://buy.moonpay.io?apiKey=pk_live_Mx5g6bpD6Etd7T0bupthv7smoTNn2Vr" +
                    "&currencyCode=btc" +
                    "&colorCode=%23055DEB" +
                    "&redirectURL=https%3A%2F%2Fbuy.moonpay.io%2Ftransaction_receipt%3FaddFunds%3Dtrue" +
                    "&enabledPaymentMethods=credit_debit_card%2Csepa_bank_transfer%2Cgbp_bank_transfer" +
                    "&walletAddress=bc1qc5jpphrj07w4lt8hfzrptpyf28j70ulduc2kqyqf09wz77tgkymq688frn" +
                    "&maxQuoteCurrencyAmount=0.00900000" +
                    "&signature=bEqOeZ2634glTtOPYCwRV1gN5kXi4FaEDcKFiymxJR4%3D"
        ) {}
    }
}
