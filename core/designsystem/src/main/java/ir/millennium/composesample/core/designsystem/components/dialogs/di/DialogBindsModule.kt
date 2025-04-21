package ir.millennium.composesample.core.designsystem.components.dialogs.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.millennium.composesample.core.designsystem.components.dialogs.factory.IMyDialogFactory
import ir.millennium.composesample.core.designsystem.components.dialogs.factory.MyDialogFactoryImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DialogBindsModule {

    @Binds
    abstract fun bindMyDialogFactory(
        myDialogFactoryImpl: MyDialogFactoryImpl
    ): IMyDialogFactory
}
