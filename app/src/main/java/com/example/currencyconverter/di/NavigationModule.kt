package com.example.currencyconverter.di

import com.example.currencyconverter.choosecurrency.ChooseCurrencyRouter
import com.example.currencyconverter.feature.result.ResultRouter
import com.example.currencyconverter.navigation.ChooseCurrencyRouterImpl
import com.example.currencyconverter.navigation.ResultRouterImpl
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface NavigationModule {
    companion object {
        @Singleton
        @Provides
        fun provideCicerone() = Cicerone.create()

        @Provides
        @Singleton
        fun provideRouter(cicerone: Cicerone<Router>): Router {
            return cicerone.router
        }

        @Provides
        @Singleton
        fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
            return cicerone.getNavigatorHolder()
        }
    }

    @Binds
    fun bindChooseCurrencyRouterImpl(impl: ChooseCurrencyRouterImpl): ChooseCurrencyRouter

    @Binds
    fun bindResultRouterImpl(impl: ResultRouterImpl): ResultRouter
}