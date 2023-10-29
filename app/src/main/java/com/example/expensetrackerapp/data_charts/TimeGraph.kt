package com.example.expensetrackerapp.data_charts

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.barchart.BarChart
import co.yml.charts.ui.barchart.models.BarChartData
import co.yml.charts.ui.barchart.models.BarData
import com.example.expensetrackerapp.viewmodel.CardListViewModel

@Composable
fun YourMainComposable(navController: NavController, viewModel: CardListViewModel) {
    val categoryAmountData = viewModel.categoryAmountData
    val mid_purple = Color(0xFFEAD7BB)

    Column(
        modifier = Modifier
            .fillMaxSize()

            .background(mid_purple)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Overview",
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.padding(4.dp))
        BarChartScreen(categoryAmountData)
    }
}


@Composable
fun BarChartScreen(categoryAmountData: List<CategoryAmountData>) {
    val textColor = Color(0xFF113946)
    val light_purple = Color(0xFFBCA37F)

    val stepsize = 5
    val barsdata = categoryAmountData.mapIndexed { index, data ->
        BarData(
            point = Point(
                index.toFloat(),
                data.amount.toFloat()
            ), // Create a Point object with index and amount
            color = textColor,  // You can set a color as needed
            label = data.category,
            gradientColorList = listOf(Color.Blue, Color.Cyan),  // Specify gradient colors
            description = "Category: ${data.category}, Amount: ${data.amount}"
        )
    }

    val xAxisData = AxisData.Builder()
        .axisStepSize(30.dp)
        .steps(barsdata.size - 1)
        .bottomPadding(40.dp)
        .axisLabelAngle(20f)
        .labelData { index -> barsdata[index].label }
        .build()

    val yAxisData = AxisData.Builder()
        .steps(stepsize)
        .labelAndAxisLinePadding(20.dp)
        .axisOffset(20.dp)
        .labelData { index -> (index * (100 / stepsize)).toString() }
        .build()

    val barChartData = BarChartData(
        chartData = barsdata,
        xAxisData = xAxisData,
        yAxisData = yAxisData,

        )
    Card(
        modifier = Modifier
            .padding(16.dp)
            .border(
                2.dp, Color.Black, shape = RoundedCornerShape(8.dp),
            ),
        colors = CardDefaults.cardColors(
            containerColor = light_purple,
        )
    ) {
        BarChart(modifier = Modifier.height(350.dp), barChartData = barChartData)
    }

}
