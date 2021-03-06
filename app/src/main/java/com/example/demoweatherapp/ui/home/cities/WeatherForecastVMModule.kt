package com.example.demoweatherapp.ui.home.cities

import androidx.lifecycle.ViewModel
import com.example.demoweatherapp.viewModel.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WeatherForecastVMModule {
    @Binds
    @IntoMap
    @ViewModelKey(WeatherForecastViewModel::class)
    abstract fun bindWeatherForecastViewModel (viewModel : WeatherForecastViewModel): ViewModel

}