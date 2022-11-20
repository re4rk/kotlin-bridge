package bridge.domain

import bridge.resources.ERROR_INPUT_COMMAND

enum class BridgeGameStatus {
    SUCCESS,
    FAILURE,
    RUNNING,
    RETRY,
    QUIT;

    companion object {
        fun getStatus(bridgeGameResult: BridgeGameInfo): BridgeGameStatus {
            bridgeGameResult.apply {
                return when {
                    stage.slice(0 until stage.size - 1).firstOrNull { it.not() } != null ->
                        throw IllegalArgumentException(ERROR_INPUT_COMMAND)
                    stage.size !in 0..bridge.size -> throw IllegalArgumentException(ERROR_INPUT_COMMAND)
                    stage.size == bridge.size && stage.last() -> SUCCESS
                    stage.size <= bridge.size && stage.last().not() -> FAILURE
                    else -> RUNNING
                }
            }
        }

        fun setStatus(command: String): BridgeGameStatus {
            return when (command) {
                "R" -> RETRY
                "Q" -> QUIT
                else -> throw IllegalArgumentException(ERROR_INPUT_COMMAND)
            }
        }
    }
}
