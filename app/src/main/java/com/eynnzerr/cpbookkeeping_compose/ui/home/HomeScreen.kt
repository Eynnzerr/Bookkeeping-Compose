package com.eynnzerr.cpbookkeeping_compose.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eynnzerr.cpbookkeeping_compose.R
import com.eynnzerr.cpbookkeeping_compose.data.fakeList
import com.eynnzerr.cpbookkeeping_compose.ui.basic.BillList
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(uiState: HomeUiState, listState: LazyListState) {
    Column() {
        BalanceSurface(
            expenses = uiState.homeData.monthlyExpense,
            revenue = uiState.homeData.monthlyRevenue,
            budget = uiState.homeData.budget
        )
        Spacer(modifier = Modifier.padding(20.dp))
        BalanceToday(
            expenses_today = uiState.homeData.dailyExpense,
            revenue_today = uiState.homeData.dailyRevenue
        )
        BillList(bills = fakeList, listState)
    }
}

@Composable
fun BalanceSurface(expenses: Float, revenue: Float, budget: Float) {
    var eye_closed by remember{ mutableStateOf(false) }
    Surface(
        shape = RoundedCornerShape(10.dp),
        elevation = 10.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(5.dp)
        ) {
            Text(
                text = stringResource(id = R.string.monthly_expenses),
                style = MaterialTheme.typography.body2
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RMBMount(money = expenses, hint = stringResource(id = R.string.no_expenses))
                Spacer(modifier = Modifier.padding(horizontal = 100.dp))
                IconButton(
                    onClick = { eye_closed = !eye_closed },
                    modifier = Modifier.padding(end = 10.dp)
                ) {
                    Icon(
                        painter = if(eye_closed) painterResource(id = R.drawable.ic_eye_closed) 
                        else painterResource(id = R.drawable.eye_open),
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Row() {
                Text(
                    text = stringResource(id = R.string.monthly_revenue),
                    style = MaterialTheme.typography.body2
                )
                //Spacer(modifier = Modifier.padding(10.dp))
                Text(
                    text = stringResource(id = R.string.remained_budget),
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(start = 210.dp, end = 5.dp)
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
            ) {
                RMBMount(money = revenue, hint = stringResource(id = R.string.no_revenue))
                Spacer(modifier = Modifier.padding(horizontal = 80.dp))
                RMBMount(money = budget)
            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Transparent,
                    contentColor = MaterialTheme.colors.primary
                ),
                elevation = null
            ) {
                Icon(
                    painterResource(id = R.drawable.ic_profit),
                    contentDescription = null,
                    modifier = Modifier.size(ButtonDefaults.IconSize)
                )
                Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    text = stringResource(id = R.string.visulized_analysis),
                    style = MaterialTheme.typography.body2
                )
            }

        }
    }
}

@Composable
fun RMBMount(money: Float, hint: String = "¥0.00") {
    if(money == 0f) {
        Text(
            text = hint,
            style = MaterialTheme.typography.h5
        )
    } else {
        val amount = "¥" + String.format("%.2f", money)
        Text(
            text = amount,
            style = MaterialTheme.typography.h5
        )
    }
}

@Composable
fun BalanceToday(expenses_today: Float, revenue_today: Float) {
    val sb = StringBuilder()
    val infoToday = with (sb) {
        append(stringResource(id = R.string.daily_expenses))
        append("  ¥")
        append(String.format("%.2f", expenses_today))
        append("    ")
        append(stringResource(id = R.string.daily_revenue))
        append("  ¥")
        append(String.format("%.2f", revenue_today))
        toString()
    }
    Text(
        text = infoToday,
        style = MaterialTheme.typography.body1,
        fontSize = 21.sp,
        modifier = Modifier.padding(5.dp)
    )
}

@Preview(
    name = "homeScreen",
    showBackground = true
)
@Composable
fun Preview() {
    Column() {
        BalanceSurface(expenses = 100.00f, revenue = 50.00f, budget = 200.00f)
        BalanceToday(expenses_today = 56.50f, revenue_today = 40.00f)
        //BillList(bills = fakeList, )
    }
}