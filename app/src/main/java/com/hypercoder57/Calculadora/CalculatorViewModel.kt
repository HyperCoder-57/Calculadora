package com.hypercoder57.Calculadora

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CalculatorViewModel : ViewModel() {

    var state by mutableStateOf(CalculatorState())

    // Funcion que maneja las distintas acciones del usuario
    fun onAction(action: CalculatorAction) {
        when(action) {
            is CalculatorAction.Number -> enterNumber(action.number)
            is CalculatorAction.Delete -> delete()
            is CalculatorAction.Clear -> state = CalculatorState()  // Reinicia el estado
            is CalculatorAction.Operation -> enterOperation(action.operation)
            is CalculatorAction.Decimal -> enterDecimal()
            is CalculatorAction.Calculate -> calculate()
        }
    }

    // Maneja la introduccion de una operacion (suma, resta, etc.)
    private fun enterOperation(operation: CalculatorOperation) {
        if (state.number1.isNotBlank()) {
            state = state.copy(operation = operation)
        }
    }

    // Realiza el calculo de la operacion seleccionada
    private fun calculate() {
        val number1 = state.number1.toDoubleOrNull()
        val number2 = state.number2.toDoubleOrNull()
        if (number1 != null && number2 != null) {
            val result = when (state.operation) {
                is CalculatorOperation.Add -> number1 + number2
                is CalculatorOperation.Subtract -> number1 - number2
                is CalculatorOperation.Multiply -> number1 * number2
                is CalculatorOperation.Divide -> number1 / number2
                null -> return
            }
            state = state.copy(
                number1 = result.toString().take(15),
                number2 = "",
                operation = null
            )
        }
    }

    // Elimina el ultimo caracter segun el estado de los numeros y la operacion
    private fun delete() {
        when {
            state.number2.isNotBlank() -> state = state.copy(number2 = state.number2.dropLast(1))
            state.operation != null -> state = state.copy(operation = null)
            state.number1.isNotBlank() -> state = state.copy(number1 = state.number1.dropLast(1))
        }
    }

    // Maneja la introduccion del punto decimal
    private fun enterDecimal() {
        when {
            state.operation == null && !state.number1.contains(".") && state.number1.isNotBlank() ->
                state = state.copy(number1 = state.number1 + ".")
            !state.number2.contains(".") && state.number2.isNotBlank() ->
                state = state.copy(number2 = state.number2 + ".")
        }
    }

    // Añade un numero al estado, dependiendo de si es para el primer o segundo numero
    private fun enterNumber(number: Int) {
        if (state.operation == null && state.number1.length < MAX_NUM_LENGTH) {
            state = state.copy(number1 = state.number1 + number)
        } else if (state.number2.length < MAX_NUM_LENGTH) {
            state = state.copy(number2 = state.number2 + number)
        }
    }

    companion object {
        private const val MAX_NUM_LENGTH = 8 // Longitud maxima del numero
    }
}
