package ir.millennium.composesample.core.designsystem.components.dialogs.factory

import ir.millennium.composesample.core.designsystem.components.dialogs.component.IMyDialogComponent
import ir.millennium.composesample.core.designsystem.components.dialogs.component.MyDialogAboutMeComponent
import ir.millennium.composesample.core.designsystem.components.dialogs.component.MyDialogQuestionComponent
import ir.millennium.composesample.core.designsystem.components.dialogs.config.MyDialogConfig
import javax.inject.Inject

open class MyDialogFactoryImpl @Inject constructor(
    private val myDialogQuestionComponent: MyDialogQuestionComponent,
    private val myDialogAboutMeComponent: MyDialogAboutMeComponent
) : IMyDialogFactory {

    override fun createDialog(myDialogConfig: MyDialogConfig): IMyDialogComponent {
        return when (myDialogConfig) {
            is MyDialogConfig.MyDialogQuestionConfig -> myDialogQuestionComponent.create(
                myDialogConfig
            )

            is MyDialogConfig.MyDialogAboutMeConfig -> myDialogAboutMeComponent.createDialog(
                myDialogConfig
            )
        }
    }
}