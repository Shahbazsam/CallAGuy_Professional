package com.example.callaguy_professional.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.callaguy_professional.core.auth.SharedPrefTokenProvider
import com.example.callaguy_professional.core.auth.TokenProvider
import com.example.callaguy_professional.core.data.HttpClientFactory
import com.example.callaguy_professional.professional.data.network.serviceList.ServiceDataSource
import com.example.callaguy_professional.professional.data.network.serviceList.ServiceDataSourceImpl
import com.example.callaguy_professional.professional.data.repository.ServiceRepoImpl
import com.example.callaguy_professional.professional.domain.ServiceRepository
import com.example.callaguy_professional.professional.presentation.service_list.ServiceRequestViewModel
import com.example.callaguy_professional.professional.presentation.sharedViewModel.ServiceListSharedViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    //Shared Preferences
    single<SharedPreferences> {
        androidContext().getSharedPreferences("prefs", MODE_PRIVATE)
    }
    //Token Provider
    single<TokenProvider> {
        SharedPrefTokenProvider(get())
    }
    //HttpClient
    single { HttpClientFactory.create(get()) }

    // DataSource
    //singleOf(::ServiceListDataSourceImpl).bind<ServiceListDataSource>() Both are same
    single<ServiceDataSource> { ServiceDataSourceImpl(httpClient = get()) }

    //Repository
    //singleOf(::ServiceListRepoImpl).bind<ServiceListRepository>()
    single<ServiceRepository> { ServiceRepoImpl(serviceDataSource = get()) }


    //viewModel
    viewModelOf(::ServiceRequestViewModel)
    viewModelOf(::ServiceListSharedViewModel)

}