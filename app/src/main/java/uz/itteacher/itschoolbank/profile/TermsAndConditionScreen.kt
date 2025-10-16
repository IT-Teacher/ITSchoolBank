package uz.itteacher.itschoolbank.profile


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import uz.itteacher.itschoolbank.R

@Composable
fun TermsAndConditionScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.LightGray.copy(alpha = 0.3f), CircleShape)
                    .clickable { },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.Black,
                    modifier = Modifier.size(18.dp).clickable{navController.popBackStack()}
                )
            }

            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "Terms & Condition",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Text(
                text = "15.1 Thank you for visiting our Application Doctor 24×7 and enrolling as a member.",
                fontSize = 15.sp,
                color = Color.Black,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "15.2 Your privacy is important to us. To better protect your privacy, we are providing this notice explaining our policy with regards to the information you share with us. This privacy policy relates to the information we collect, online from Application, received through the email, by fax or telephone, or in person or in any other way and retain and use for the purpose of providing you services. If you do not agree to the terms in this Policy, we kindly ask you not to use these portals and/or sign the contract document.",
                fontSize = 15.sp,
                color = Color.Black,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "15.3 In order to use the services of this Application, You are required to register yourself by verifying the authorised device. This Privacy Policy applies to your information that we collect and receive on and through Doctor 24×7; it does not apply to practices of businesses that we do not own or control or people we do not employ.",
                fontSize = 15.sp,
                color = Color.Black,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "15.4 By using this Application, you agree to the terms of this Privacy Policy.",
                fontSize = 15.sp,
                color = Color.Black,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
