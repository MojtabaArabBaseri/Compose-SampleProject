package ir.millennium.composesample.core.designsystem.components.dialogs.factory

import ir.millennium.composesample.core.designsystem.components.dialogs.component.IMyDialogComponent
import ir.millennium.composesample.core.designsystem.components.dialogs.config.MyDialogConfig

interface IMyDialogFactory {
    fun createDialog(myDialogConfig: MyDialogConfig): IMyDialogComponent
}