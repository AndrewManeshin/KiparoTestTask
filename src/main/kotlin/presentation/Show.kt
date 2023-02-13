package presentation

interface Show {

    fun show(title: String, description: String, date: String)
    fun show(text: String)

    class Base : Show {

        override fun show(title: String, description: String, date: String) {
            println("$LINE$title$SPACE$date\n$description")
        }

        override fun show(text: String) {
            print(text)
        }

        private companion object {
            private const val LINE = "\n--------------------------\n"
            private const val SPACE = "        "
        }
    }
}