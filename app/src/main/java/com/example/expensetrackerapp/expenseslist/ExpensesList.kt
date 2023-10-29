package com.example.expensetrackerapp.expenseslist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.expensetrackerapp.R
import com.example.expensetrackerapp.room_database.DetailEvent
import com.example.expensetrackerapp.room_database.DetailState
import com.example.expensetrackerapp.room_database.SortType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardListScreen2(navController: NavController,
                   state:DetailState,
                   onEvent:(DetailEvent)->Unit,

) {


    val textColor = Color(0xFF113946)
    val mid_purple = Color(0xFFEAD7BB)
    val light_purple = Color(0xFFBCA37F)
    val bgCOlor = Color(0xFFFFF8C9)
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(mid_purple)
    ) {
        LazyColumn {

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    verticalAlignment = CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    SortType.values().forEach { sortType ->
                        if (sortType != SortType.values().first()) {
                            Spacer(modifier = Modifier.width(8.dp)) // Adjust the width as needed
                        }
//                        Row(
//                            modifier = Modifier.clickable {
//                                onEvent(DetailEvent.SortDetails(sortType = sortType))
//                            },
//                            verticalAlignment = CenterVertically
//                        ) {
//                            RadioButton(selected = state.sortType == sortType,
//                                onClick = { onEvent(DetailEvent.SortDetails(sortType = sortType)) })
//                            Text(text = sortType.name)
//                        }
                        FilterChip(
                            selected = state.sortType == sortType,
                            onClick = {  onEvent(DetailEvent.SortDetails(sortType = sortType))
                            },
                            label = {
                                Text(text = sortType.name)
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = light_purple, // Set the color when the chip is selected
                                selectedLabelColor = textColor, // Set the label color when the chip is selected
                                 // Set the icon color when the chip is selected
                                // Set the color when the chip is not selected
                            )
                        )


                    }
                }
            }

            //philip bhai
            items(state.details) { detail ->
                Card(
                    modifier = Modifier
                        .padding(16.dp)
                        // Change the card background color
                        .border(
                            2.dp, Color.Black, shape = RoundedCornerShape(8.dp),
                        ),
                    colors = CardDefaults.cardColors(
                        containerColor = light_purple,
                    )
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(modifier = Modifier.weight(1f)) {

//                            Text(
//                                text = " Date:${detail.date}",
//                                fontSize = 20.sp,
//                                fontWeight = FontWeight.SemiBold
//                            )
                            Text(
                                text = " Category:${detail.category}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light
                            )
                            Text(
                                text = " Amount:${detail.amount}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light
                            )
                            Text(
                                text = " Desc:${detail.desc}",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Light
                            )
                        }
                        IconButton(onClick = {
                            onEvent(DetailEvent.DeleteDetails(detail = detail))
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "delete")
                        }
                    }
                }

            }
            item() {
                Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(10.dp)) {
                    Button(
                        onClick = { navController.navigate("chartScreen") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .background(Color.Black, shape = RoundedCornerShape(percent = 50)),
                        colors = ButtonDefaults.buttonColors(colorResource(id =R.color.black) ) // Set text color to white
                    ) {
                        Text("Explore chart")
                    }
                    }
                }
            }
        }
    }
