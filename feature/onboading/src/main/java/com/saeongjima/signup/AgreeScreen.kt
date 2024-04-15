package com.saeongjima.signup

import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.saeongjima.designsystem.component.checkbox.DanjamCheckboxWithLabel
import com.saeongjima.designsystem.theme.Black200
import com.saeongjima.designsystem.theme.Black500
import com.saeongjima.designsystem.theme.Black700
import com.saeongjima.designsystem.theme.Black950
import com.saeongjima.login.R

@Composable
fun AgreeScreen(modifier: Modifier = Modifier, enableButton: (Boolean) -> Unit) {
    var openDialog by rememberSaveable { mutableStateOf(false) }
    var dialogText by rememberSaveable { mutableStateOf(PolicyText.TermsOfUse) }
    var isCheckedTermsOfUse by rememberSaveable { mutableStateOf(false) }
    var isCheckedPrivacyPolicy by rememberSaveable { mutableStateOf(false) }

    if (isCheckedTermsOfUse && isCheckedPrivacyPolicy) enableButton(true) else enableButton(false)

    if (openDialog) {
        FullTextDialog(text = stringResource(id = dialogText.res)) {
            openDialog = false
        }
    }

    Column {
        Text(
            text = stringResource(R.string.request_agree_for_service),
            style = MaterialTheme.typography.displayLarge,
            color = Black950,
            modifier = modifier.padding(top = 64.dp),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 36.dp),
        ) {
            DanjamCheckboxWithLabel(
                isChecked = isCheckedTermsOfUse,
                label = stringResource(R.string.terms_of_use_title),
                onValueChange = { isCheckedTermsOfUse = !isCheckedTermsOfUse },
                modifier = Modifier.padding(start = 16.dp),
            )
            Text(
                text = stringResource(R.string.see_full_text),
                textDecoration = TextDecoration.Underline,
                color = Black500,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clickable {
                        openDialog = true
                        dialogText = PolicyText.TermsOfUse
                    },
            )
        }
        DrawScrollableView(
            modifier = modifier
                .padding(top = 12.dp)
                .weight(1f)
                .background(Black200, shape = RoundedCornerShape(4.dp)),
        ) {
            Text(
                text = stringResource(R.string.terms_of_use),
                style = MaterialTheme.typography.labelLarge,
                color = Black700,
            )
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
        ) {
            DanjamCheckboxWithLabel(
                isChecked = isCheckedPrivacyPolicy,
                label = stringResource(R.string.privacy_policy_title),
                onValueChange = { isCheckedPrivacyPolicy = !isCheckedPrivacyPolicy },
                modifier = Modifier.padding(start = 16.dp),
            )
            Text(
                text = stringResource(id = R.string.see_full_text),
                textDecoration = TextDecoration.Underline,
                color = Black500,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(end = 24.dp)
                    .clickable {
                        openDialog = true
                        dialogText = PolicyText.PrivacyPolicy
                    },
            )
        }
        DrawScrollableView(
            modifier = modifier
                .padding(top = 12.dp)
                .weight(1f)
                .background(Black200, shape = RoundedCornerShape(4.dp)),
        ) {
            Text(
                text = stringResource(R.string.privacy_policy),
                style = MaterialTheme.typography.labelLarge,
                color = Black700,
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
fun FullTextDialog(text: String, modifier: Modifier = Modifier, onDismissRequest: () -> Unit) {
    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.8f)
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 12.dp)
        ) {
            IconButton(
                onClick = { onDismissRequest() },
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.End)
            ) {
                Icon(
                    painter = painterResource(id = com.saeongjima.designsystem.R.drawable.ic_exit_24),
                    contentDescription = stringResource(R.string.exit_see_full_text),
                    modifier = modifier.size(36.dp),
                    tint = Black700,
                )
            }
            DrawScrollableView(
                modifier = modifier
                    .padding(top = 12.dp)
                    .weight(1f)
                    .background(Black200, shape = RoundedCornerShape(4.dp)),
            ) {
                Text(
                    text = text,
                    style = MaterialTheme.typography.labelLarge,
                    color = Black700,
                )
            }
        }
    }
}

@Composable
fun DrawScrollableView(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    val context = LocalContext.current
    AndroidView(
        modifier = modifier.padding(16.dp),
        factory = {
            val scrollView = ScrollView(it)
            val layout = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
            scrollView.layoutParams = layout
            scrollView.isVerticalFadingEdgeEnabled = true
            scrollView.isScrollbarFadingEnabled = false
            scrollView.isVerticalScrollBarEnabled = true
            scrollView.scrollBarSize = 12
            scrollView.scrollBarStyle = View.SCROLLBARS_OUTSIDE_INSET
            scrollView.isSmoothScrollingEnabled = true
            scrollView.verticalScrollbarThumbDrawable = AppCompatResources.getDrawable(
                context,
                com.saeongjima.designsystem.R.drawable.scroll_bar,
            )
            scrollView.addView(
                ComposeView(it).apply {
                    setContent {
                        content()
                    }
                },
            )
            val linearLayout = LinearLayout(it)
            linearLayout.orientation = LinearLayout.VERTICAL
            linearLayout.layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT)
            linearLayout.addView(scrollView)
            linearLayout
        },
    )
}

enum class PolicyText(@StringRes val res: Int) {
    TermsOfUse(R.string.terms_of_use),
    PrivacyPolicy(R.string.privacy_policy),
}
