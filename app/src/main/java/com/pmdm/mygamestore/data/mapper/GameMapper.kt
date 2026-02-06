package com.pmdm.mygamestore.data.mapper

import com.pmdm.mygamestore.data.local.entities.LibraryEntity
import com.pmdm.mygamestore.data.local.entities.GameNoteEntity
import com.pmdm.mygamestore.data.local.entities.UserEntity
import com.pmdm.mygamestore.domain.model.Game
import com.pmdm.mygamestore.domain.model.LibraryStatus
import com.pmdm.mygamestore.domain.model.GameProgress

/**
 * Mappers para convertir entre Entidades de Room y Modelos de Dominio.
 */

fun LibraryEntity.toDomainStatus(): LibraryStatus = this.status

fun GameNoteEntity.toDomainProgress(): GameProgress = this.progressStatus
