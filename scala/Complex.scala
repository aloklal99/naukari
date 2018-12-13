class Complex1(real: Double, imaginary: Double) {
    def re() = real
    def im() = imaginary
    override def toString() =
        f"${re()}%2.3f ${if (im() < 0) '-' else '+'}%s ${im()}%02.2e"
}

class Complex(real: Double, imaginary: Double) {
    def re = real
    def im = imaginary
    override def toString() =
        "" + re + (if (im < 0) "-" else "+") + im + "i"
}
