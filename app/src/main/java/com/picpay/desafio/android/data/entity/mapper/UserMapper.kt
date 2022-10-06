package com.picpay.desafio.android.data.entity.mapper

import com.picpay.desafio.android.data.entity.UserDTO
import com.picpay.desafio.android.domain.model.UserModel

fun UserDTO.toModel() =
    UserModel(
        id = id,
        img = img,
        name = name,
        username = username
    )

fun List<UserDTO>.toModelList(): List<UserModel> =
    this.map {
        it.toModel()
    }

fun UserModel.toDto() =
    UserDTO(
        id = id,
        img = img,
        name = name,
        username = username
    )

fun List<UserModel>.toDTOList(): List<UserDTO> =
    this.map {
        it.toDto()
    }
