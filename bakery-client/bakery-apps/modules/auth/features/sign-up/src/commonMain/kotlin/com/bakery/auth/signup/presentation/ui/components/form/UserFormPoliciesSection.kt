package com.bakery.auth.signup.presentation.ui.components.form

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import com.bakery.auth.ui.AuthFormItemSection
import com.bakery.resources.generated.resources.Res
import com.bakery.resources.generated.resources.accept_privacy_policy
import com.bakery.resources.generated.resources.accept_terms_and_conditions
import com.bakery.ui.components.check.CheckBoxRow
import com.bakery.ui.components.display.TextComponent
import org.jetbrains.compose.resources.stringResource

@Composable
fun UserFormPoliciesSection(
    termsAndConditions: Boolean,
    privacyPolicy: Boolean,
    onTermsAndConditionsChange: () -> Unit,
    onPrivacyPolicyChange: () -> Unit,
    onTerms: () -> Unit,
    onPrivacyPolicy: () -> Unit,
    modifier: Modifier = Modifier,
    termsAndConditionsError: String? = null,
    privacyPolicyError: String? = null,
) {
    AuthFormItemSection(
        modifier = modifier,
    ) {
        CheckBoxRow(
            modifier = Modifier.fillMaxWidth(),
            checked = termsAndConditions,
            onCheckedChange = { _ ->
                onTermsAndConditionsChange()
            },
            label = {
                TextComponent(
                    modifier = Modifier
                        .clickable(
                            onClick = onTerms
                        ),
                    text = stringResource(Res.string.accept_terms_and_conditions),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                    ),
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                        maxFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    )
                )
            },
            supportingText = termsAndConditionsError?.let { error ->
                {
                    TextComponent(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                            maxFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        )
                    )
                }
            }
        )

        CheckBoxRow(
            modifier = Modifier.fillMaxWidth(),
            checked = privacyPolicy,
            onCheckedChange = { _ ->
                onPrivacyPolicyChange()
            },
            label = {
                TextComponent(
                    modifier = Modifier
                        .clickable(
                            onClick = onPrivacyPolicy
                        ),
                    text = stringResource(Res.string.accept_privacy_policy),
                    style = TextStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                    ),
                    autoSize = TextAutoSize.StepBased(
                        minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                        maxFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    )
                )
            },
            supportingText = privacyPolicyError?.let { error ->
                {
                    TextComponent(
                        text = error,
                        color = MaterialTheme.colorScheme.error,
                        autoSize = TextAutoSize.StepBased(
                            minFontSize = MaterialTheme.typography.labelSmall.fontSize,
                            maxFontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        )
                    )
                }
            }
        )
    }
}
