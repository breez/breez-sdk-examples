package sample.sdk.breez.page.home.buybitcoin

import breez_sdk.BuyBitcoinProvider

sealed class BuyBitcoinState {

    data class Initial(
        val initialProvider: BuyBitcoinProvider = BuyBitcoinProvider.MOONPAY,
    ) : BuyBitcoinState()

    object Loading : BuyBitcoinState()

    data class Error(
        val throwable: Throwable,
    ) : BuyBitcoinState()

    data class Success(
        val url: String,
    ) : BuyBitcoinState()

}
