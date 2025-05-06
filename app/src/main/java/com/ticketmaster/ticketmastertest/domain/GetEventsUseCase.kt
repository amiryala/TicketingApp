package com.ticketmaster.ticketmastertest.domain

import org.koin.core.annotation.Factory

@Factory
class GetEventsUseCase {
    suspend operator fun invoke(): List<DomainEvent> = emptyList()
}
