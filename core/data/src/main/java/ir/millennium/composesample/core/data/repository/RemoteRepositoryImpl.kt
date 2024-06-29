package ir.millennium.composesample.core.data.repository

import ir.millennium.composesample.core.network.dataSource.ApiService


class RemoteRepositoryImpl(private val apiService: ApiService) : RemoteRepository