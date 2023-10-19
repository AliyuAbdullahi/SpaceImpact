package com.lek.spaceimpact.game.di

import android.content.Context
import com.lek.spaceimpact.game.data.SoundService
import com.lek.spaceimpact.game.domain.ISoundService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object SoundServiceModule {

    @Provides
    @Singleton
    fun provideSoundService(@ApplicationContext context: Context): ISoundService =
        SoundService(context)
}