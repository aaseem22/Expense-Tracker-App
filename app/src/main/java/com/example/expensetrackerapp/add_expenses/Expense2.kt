package com.example.expensetrackerapp.add_expenses


import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerapp.room_database.DetailEvent
import com.example.expensetrackerapp.room_database.DetailState
import com.example.expensetrackerapp.viewmodel.CardListViewModel
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddExpenses1(
    navController: NavController,
    onEvent: (DetailEvent) -> Unit,
    state: DetailState,
    viewModel: CardListViewModel,
) {
    val textColor = Color(0xFF113946)
    val mid_purple = Color(0xFFEAD7BB)
    val light_purple = Color(0xFFBCA37F)
    val bgCOlor = Color(0xFFFFF8C9)
    Column(
        modifier = Modifier

            .fillMaxHeight()
            .background(mid_purple)

    ) {


        val options = listOf("Option 1", "Option 2", "Option 3")
        var expanded1 by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }

        val context = LocalContext.current
        val categories = arrayOf("House", "Clothes", "Food", "Bills", "Subscriptions")
        var expanded by remember { mutableStateOf(false) }

        var selectedCategory by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var isUserBelow18 by remember {
            mutableStateOf(false)
        }
        val mContext = LocalContext.current

        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()


        val mDate = remember { mutableStateOf("") }

        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, mYear, mMonth, mDay
        )

        val selectedDate = mDate.value
//
        Spacer(modifier = Modifier.padding(2.dp))
        Text(
            text = "Add Expenses",
            textAlign = TextAlign.Start,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 40.sp, modifier = Modifier
                .fillMaxWidth()
                .padding(start = 6.dp),
            style = TextStyle(
                color = textColor
            )
        )
        Spacer(modifier = Modifier.padding(4.dp))

        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Date",
                textAlign = TextAlign.Left,
                fontWeight = FontWeight.SemiBold,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                style = TextStyle(
                    color = textColor
                )
            )

            var text by remember { mutableStateOf(TextFieldValue()) }

            val maxChar = 8
            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    singleLine = true,
                    value = state.date,
                    onValueChange = {
                        // if (it.length <= maxChar) text = it
                        onEvent(DetailEvent.SetDate(it))
                    },
                    visualTransformation = DateTransformation(),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,

                        ),
                    label = { Text(text = "DD/MM/YYYY") },
                    placeholder = { Text(text = "Enter date") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = mid_purple,
                        unfocusedContainerColor = mid_purple,
                        disabledContainerColor = mid_purple,
                        cursorColor = textColor,
                        focusedIndicatorColor = light_purple, // Change the indication color
                        unfocusedIndicatorColor = light_purple, // Change the indication color
                    ),
                )
            }
        }
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Category",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                style = TextStyle(
                    color = textColor
                )
            )
            Demo_ExposedDropdownMenuBox1(
                onCategorySelected = { selectedCategory = it },
                state,
                onEvent
            )
        }
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Amount",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                style = TextStyle(
                    color = textColor
                )
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //amountInput
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
//                    modifier = Modifier.padding(12.dp)
                ) {
                    OutlinedTextField(
                        value = state.amount,
                        onValueChange = {
                            onEvent(DetailEvent.SetAmount(it))
                            //state.amount = it
                            isUserBelow18 = false

                        },
                        label = { Text(text = "Amount") },
                        placeholder = { Text(text = "Enter amount") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.ShoppingCart,
                                contentDescription = "Lock Icon"
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = mid_purple,
                            unfocusedContainerColor = mid_purple,
                            disabledContainerColor = mid_purple,
                            cursorColor = textColor,
                            focusedIndicatorColor = light_purple, // Change the indication color
                            unfocusedIndicatorColor = light_purple, // Change the indication color
                        ),
                        isError = isUserBelow18,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done,

                            ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                isUserBelow18 = validateAge(inputText = state.amount)
                            }
                        )
                    )

                    if (isUserBelow18) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = "Amount cannot be negative",
                            color = Color.Red
                        )
                    }
                }
            }
        }
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Description",
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                style = TextStyle(
                    color = textColor
                )
            )
            //descriptionInput
            Column(modifier = Modifier.padding(2.dp)) {
                OutlinedTextField(
                    value = state.desc,
                    onValueChange = {
                        onEvent(DetailEvent.SetDesc(it))
                    },
                    label = { Text(text = "Description") },
                    placeholder = { Text(text = "Enter description") },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = mid_purple,
                        unfocusedContainerColor = mid_purple,
                        disabledContainerColor = mid_purple,
                        cursorColor = textColor,
                        focusedIndicatorColor = light_purple, // Change the indication color
                        unfocusedIndicatorColor = light_purple, // Change the indication color
                    ),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Edit,
                            contentDescription = "Lock Icon"
                        )
                    },

                    )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.padding(10.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = {
                        val category = state.category
                        val amount = state.amount.toDouble()
                        viewModel.addCategoryAmountData(category, amount)
                        viewModel.updateUserEnteredData(selectedCategory, state.amount)
                        onEvent(DetailEvent.SaveDetail)
                        navController.navigate("cardListScreen")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(percent = 50)) // Make the button rounded
                        .background(Color.Black), // Set the background color to black
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White) // Set text color to white
                ) {
                    Text(
                        "ADD",
                        fontSize = 25.sp
                    )
                }
            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
//selectedCategory: String,
fun Demo_ExposedDropdownMenuBox1(
    onCategorySelected: (String) -> Unit,
    state: DetailState,
    onEvent: (DetailEvent) -> Unit,
) {
    val context = LocalContext.current
    val categories = arrayOf("House", "Clothes", "Food", "Bills", "Subscriptions")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(categories[0]) }
    state.category = selectedText
    val textColor = Color(0xFF113946)
    val mid_purple = Color(0xFFEAD7BB)
    val light_purple = Color(0xFFBCA37F)
    val bgCOlor = Color(0xFFFFF8C9)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        contentAlignment = Alignment.Center
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                value = selectedText,
                onValueChange = { onEvent(DetailEvent.SetCategory(it)) },
                readOnly = true,
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier.menuAnchor(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = mid_purple,
                    unfocusedContainerColor = mid_purple,
                    disabledContainerColor = mid_purple,
                    cursorColor = textColor,
                    focusedIndicatorColor = light_purple, // Change the indication color
                    unfocusedIndicatorColor = light_purple, // Change the indication color
                ),
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(light_purple)

            ) {
                categories.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item) },
                        onClick = {
                            selectedText = item
                            onCategorySelected(item) // Pass the selected item
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}

private fun validateAge(inputText: String): Boolean {
    return inputText.toInt() < 0
}
