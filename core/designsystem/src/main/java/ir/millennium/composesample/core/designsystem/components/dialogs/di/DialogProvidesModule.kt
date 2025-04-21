package ir.millennium.composesample.core.designsystem.components.dialogs.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.millennium.composesample.core.designsystem.components.dialogs.component.MyDialogAboutMeComponent
import ir.millennium.composesample.core.designsystem.components.dialogs.component.MyDialogQuestionComponent

@Module
@InstallIn(SingletonComponent::class)
object DialogProvidesModule {

    @Provides
    fun provideQuestionMyDialogComponent(): MyDialogQuestionComponent =
        MyDialogQuestionComponent()

    @Provides
    fun provideAboutMeDialogComponent(): MyDialogAboutMeComponent =
        MyDialogAboutMeComponent()
}
