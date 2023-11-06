class Car(private val title: String, private val priceCode: Int) {
    companion object {
        const val MUSCLE = 2
        const val ECONOMY = 0
        const val SUPERCAR = 1
    }

    fun getPriceCode(): Int {
        return priceCode
    }

    fun getTitle(): String {
        return title
    }
}

class Rental(private val car: Car, private val daysRented: Int) {
    fun getDaysRented(): Int {
        return daysRented
    }

    fun getCar(): Car {
        return car
    }
}

class Customer(private val name: String) {
    private val rentals = ArrayList<Rental>()

    fun addRental(rental: Rental) {
        rentals.add(rental)
    }

    private fun getName(): String {
        return name
    }

    fun billingStatement(): String {
        var totalAmount = 0.0
        var frequentRenterPoints = 0
        val builder = StringBuilder()
        builder.append("Rental Record for ")
        builder.appendLine(getName())

        for (rental in rentals) {
            var thisAmount = calculateRentalAmount(rental)

            // Add frequent renter points
            frequentRenterPoints++
            // Add bonus for a two-day new release rental
            if (rental.getCar().getPriceCode() == Car.SUPERCAR && rental.getDaysRented() > 1) {
                frequentRenterPoints++
            }

            // Show figures for this rental
            builder.append("\t")
            builder.append(rental.getCar().getTitle())
            builder.append("\t")
            builder.appendLine(thisAmount.toString())
            totalAmount += thisAmount
        }

        // Add footer lines
        builder.appendLine("Final rental payment owed $totalAmount")
        builder.append("You received an additional $frequentRenterPoints frequent customer points")
        return builder.toString()
    }

    private fun calculateRentalAmount(rental: Rental): Double {
        return when (rental.getCar().getPriceCode()) {
            Car.ECONOMY -> {
                var thisAmount = 80.0
                if (rental.getDaysRented() > 2) {
                    thisAmount += ((rental.getDaysRented()) - 2).toDouble() * 30.0
                }
                thisAmount
            }
            Car.SUPERCAR -> (rental.getDaysRented()).toDouble() * 200.0
            Car.MUSCLE -> {
                var thisAmount = 200.0
                if (rental.getDaysRented() > 3) {
                    thisAmount += ((rental.getDaysRented()).toDouble() - 3) * 50.0
                }
                thisAmount
            }
            else -> 0.0
        }
    }
}

fun main() {
    val rental1 = Rental(Car("Mustang", Car.MUSCLE), 5)
    val rental2 = Rental(Car("Lambo", Car.SUPERCAR), 20)
    val customer = Customer("Liviu")

    customer.addRental(rental1)
    customer.addRental(rental2)
    println(customer.billingStatement())
}
