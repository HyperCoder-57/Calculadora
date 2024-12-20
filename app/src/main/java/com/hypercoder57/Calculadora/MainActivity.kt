package com.hypercoder57.Calculadora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hypercoder57.Calculadora.ui.theme.CalculatorPrepTheme
import com.hypercoder57.Calculadora.ui.theme.LightGray
import com.hypercoder57.Calculadora.ui.theme.MediumGray
import com.hypercoder57.Calculadora.ui.theme.Orange

@ExperimentalComposeUiApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorPrepTheme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state
                val buttonSpacing = 8.dp

                // Contenedor principal con fondo oscuro
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                        .padding(16.dp)
                ) {
                    // Columna que contiene las filas de botones
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        verticalArrangement = Arrangement.spacedBy(buttonSpacing),
                    ) {
                        // Pantalla de la calculadora
                        Text(
                            text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                            textAlign = TextAlign.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            fontWeight = FontWeight.Light,
                            fontSize = 80.sp,
                            color = Color.White,
                            maxLines = 2
                        )

                        // Filas de botones
                        ButtonRow(
                            buttons = listOf(
                                ButtonData("AC", LightGray, 2f, CalculatorAction.Clear),
                                ButtonData("Del", LightGray, 1f, CalculatorAction.Delete),
                                ButtonData("/", Orange, 1f, CalculatorAction.Operation(CalculatorOperation.Divide))
                            ),
                            viewModel = viewModel,
                            buttonSpacing = buttonSpacing
                        )
                        ButtonRow(
                            buttons = listOf(
                                ButtonData("7", MediumGray, 1f, CalculatorAction.Number(7)),
                                ButtonData("8", MediumGray, 1f, CalculatorAction.Number(8)),
                                ButtonData("9", MediumGray, 1f, CalculatorAction.Number(9)),
                                ButtonData("x", Orange, 1f, CalculatorAction.Operation(CalculatorOperation.Multiply))
                            ),
                            viewModel = viewModel,
                            buttonSpacing = buttonSpacing
                        )
                        ButtonRow(
                            buttons = listOf(
                                ButtonData("4", MediumGray, 1f, CalculatorAction.Number(4)),
                                ButtonData("5", MediumGray, 1f, CalculatorAction.Number(5)),
                                ButtonData("6", MediumGray, 1f, CalculatorAction.Number(6)),
                                ButtonData("-", Orange, 1f, CalculatorAction.Operation(CalculatorOperation.Subtract))
                            ),
                            viewModel = viewModel,
                            buttonSpacing = buttonSpacing
                        )
                        ButtonRow(
                            buttons = listOf(
                                ButtonData("1", MediumGray, 1f, CalculatorAction.Number(1)),
                                ButtonData("2", MediumGray, 1f, CalculatorAction.Number(2)),
                                ButtonData("3", MediumGray, 1f, CalculatorAction.Number(3)),
                                ButtonData("+", Orange, 1f, CalculatorAction.Operation(CalculatorOperation.Add))
                            ),
                            viewModel = viewModel,
                            buttonSpacing = buttonSpacing
                        )
                        ButtonRow(
                            buttons = listOf(
                                ButtonData("0", MediumGray, 2f, CalculatorAction.Number(0)),
                                ButtonData(".", MediumGray, 1f, CalculatorAction.Decimal),
                                ButtonData("=", Orange, 1f, CalculatorAction.Calculate)
                            ),
                            viewModel = viewModel,
                            buttonSpacing = buttonSpacing
                        )
                    }
                }
            }
        }
    }
}

// Componente reutilizable para filas de botones
@Composable
fun ButtonRow(
    buttons: List<ButtonData>,
    viewModel: CalculatorViewModel,
    buttonSpacing: Dp
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
    ) {
        buttons.forEach { button ->
            CalculatorButton(
                symbol = button.symbol,
                color = button.color,
                modifier = Modifier
                    .aspectRatio(button.aspectRatio)
                    .weight(button.weight)
            ) {
                viewModel.onAction(button.action)
            }
        }
    }
}

// Datos del botón
data class ButtonData(
    val symbol: String,
    val color: Color,
    val aspectRatio: Float,
    val action: CalculatorAction
)
