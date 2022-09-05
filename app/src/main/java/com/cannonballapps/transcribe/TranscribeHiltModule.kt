package com.cannonballapps.transcribe

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import linc.com.amplituda.Amplituda

@Module
@InstallIn(ViewModelComponent::class)
object TranscribeHiltModule {

    @Provides
    fun provideAmplituda(
        @ApplicationContext context: Context,
    ): Amplituda = Amplituda(context)
}