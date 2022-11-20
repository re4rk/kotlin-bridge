package bridge.domain

import bridge.ApplicationTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BridgeGameTest {
    private var bridge = emptyList<String>()
    private var bridgeGameInfo = BridgeGameInfo(bridge, emptyList(), 1)

    @BeforeEach
    fun beforeEach() {
        val numberGenerator: BridgeNumberGenerator = ApplicationTest.TestNumberGenerator(listOf(1, 0, 0, 0, 0))
        bridge = BridgeMaker(numberGenerator).makeBridge(3)
        bridgeGameInfo = BridgeGameInfo(bridge, emptyList(), 1)
    }

    @Test
    fun `움직일 수 있을 때 움직인다`() {
        listOf("U", "D", "U").forEach {
            bridgeGameInfo = BridgeGame().move(bridgeGameInfo, it)
        }
        assertThat(bridgeGameInfo.stage).isEqualTo(listOf(true, true, false))
    }

    @Test
    fun `끝까지 왔으면 더이상 움직이지 않는다`() {
        listOf("U", "D", "D", "D").forEach {
            bridgeGameInfo = BridgeGame().move(bridgeGameInfo, it)
        }
        assertThat(bridgeGameInfo.stage).isEqualTo(listOf(true, true, true))
    }

    @Test
    fun `재도전을하면 stage가 초기화 되고 도전 횟수 증가한다`() {
        listOf("U", "D", "D", "D").forEach {
            bridgeGameInfo = BridgeGame().move(bridgeGameInfo, it)
        }
        bridgeGameInfo = BridgeGame().retry(bridgeGameInfo)
        assertThat(bridgeGameInfo.stage).isEqualTo(emptyList<String>())
        assertThat(bridgeGameInfo.countOfTry).isEqualTo(2)
    }
}