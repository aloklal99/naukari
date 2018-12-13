object Timer {
    def timer(callback: () => Unit) {
        while (true) { callback(); Thread sleep 1000 }
    }

    def flies() {
        println("Time flies like an arrow!")
    }

    def main1(args: Array[String]) {
        timer(flies)
    }

    def main(args: Array[String]) {
        timer(() =>
            println("Time flies like an arrow!")
        )
    }
}