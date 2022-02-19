package ru.netology.nmedia

class DisplayingNumbers {

    fun displaying(number: Int): String {

        val result = if (number in 1000..1099) {
            (number / 1000).toString() + "к"
        } else if (number in 1100..999999) {
            (number / 1000).toString() + "." + (number % 1000 / 100).toString() + "к"
        } else if (number in 1000000..1099999) {
            (number / 1000000).toString() + "м"
        } else if (number >= 1100000) {
            (number / 1000000).toString() + "." + (number % 1000000 / 100000).toString() + "м"
        } else {
            return number.toString()
        }
        return result
    }

}
