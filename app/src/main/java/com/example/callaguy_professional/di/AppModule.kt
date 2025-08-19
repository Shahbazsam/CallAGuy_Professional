package com.example.callaguy_professional.di

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.callaguy_professional.core.auth.SharedPrefTokenProvider
import com.example.callaguy_professional.core.auth.TokenProvider
import com.example.callaguy_professional.core.data.HttpClientFactory
import com.example.callaguy_professional.core.domain.validation.ValidateEmail
import com.example.callaguy_professional.core.domain.validation.ValidatePassword
import com.example.callaguy_professional.professional.data.network.auth.AuthUseCase
import com.example.callaguy_professional.professional.data.network.auth.AuthUseCaseImpl
import com.example.callaguy_professional.professional.data.network.onGoing.OnGoingDataSource
import com.example.callaguy_professional.professional.data.network.onGoing.OnGoingDataSourceImpl
import com.example.callaguy_professional.professional.data.network.profile.ProfileDataSource
import com.example.callaguy_professional.professional.data.network.profile.ProfileDataSourceImpl
import com.example.callaguy_professional.professional.data.network.serviceList.ServiceDataSource
import com.example.callaguy_professional.professional.data.network.serviceList.ServiceDataSourceImpl
import com.example.callaguy_professional.professional.data.repository.AuthRepositoryImpl
import com.example.callaguy_professional.professional.data.repository.OnGoingRepositoryImpl
import com.example.callaguy_professional.professional.data.repository.ProfileRepositoryImpl
import com.example.callaguy_professional.professional.data.repository.ServiceRepoImpl
import com.example.callaguy_professional.professional.domain.AuthRepository
import com.example.callaguy_professional.professional.domain.OnGoingRepository
import com.example.callaguy_professional.professional.domain.ProfileRepository
import com.example.callaguy_professional.professional.domain.ServiceRepository
import com.example.callaguy_professional.professional.presentation.ServiceDetail.ServiceDetailViewModel
import com.example.callaguy_professional.professional.presentation.login.LoginViewModel
import com.example.callaguy_professional.professional.presentation.on_going.OnGoingViewModel
import com.example.callaguy_professional.professional.presentation.on_going_detail.OnGoingDetailViewModel
import com.example.callaguy_professional.professional.presentation.profile.ProfileViewModel
import com.example.callaguy_professional.professional.presentation.service_list.ServiceRequestViewModel
import com.example.callaguy_professional.professional.presentation.sharedViewModel.OnGoingSharedViewModel
import com.example.callaguy_professional.professional.presentation.sharedViewModel.ServiceListSharedViewModel
import com.example.callaguy_professional.professional.presentation.splash.SplashViewModel
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

    //Validation
    single { ValidateEmail() }
    single { ValidatePassword() }

    // DataSource
    //singleOf(::ServiceListDataSourceImpl).bind<ServiceListDataSource>() Both are same
    single<AuthUseCase> { AuthUseCaseImpl(get()) }
    single<ServiceDataSource> { ServiceDataSourceImpl(httpClient = get()) }
    single<OnGoingDataSource> { OnGoingDataSourceImpl(get()) }
    single<ProfileDataSource>{ ProfileDataSourceImpl(get()) }

    //Repository
    //singleOf(::ServiceListRepoImpl).bind<ServiceListRepository>()
    single<AuthRepository> { AuthRepositoryImpl(get()) }
    single<ServiceRepository> { ServiceRepoImpl(serviceDataSource = get()) }
    single<OnGoingRepository> { OnGoingRepositoryImpl(get()) }
    single<ProfileRepository> { ProfileRepositoryImpl(get()) }


    //viewModel
    viewModelOf(::SplashViewModel)
    viewModelOf(::ServiceRequestViewModel)
    viewModelOf(::ServiceDetailViewModel)
    viewModelOf(::ServiceListSharedViewModel)
    viewModelOf(::OnGoingViewModel)
    viewModelOf(::OnGoingDetailViewModel)
    viewModelOf(::OnGoingSharedViewModel)
    viewModelOf(::ProfileViewModel)
    viewModelOf(::LoginViewModel)

}