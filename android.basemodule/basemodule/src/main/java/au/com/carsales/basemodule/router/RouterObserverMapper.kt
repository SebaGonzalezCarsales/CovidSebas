package au.com.carsales.basemodule.router

import au.com.carsales.basemodule.util.commonResources.State

/**
 * Created by Dan on 01, April, 2020
 * Copyright (c) 2020 Carsales. All rights reserved.
 *
 * Used in the case that a router service class
 * needs to map observer between two modules
 */
abstract class RouterObserverMapper {

    /**
     * Maps one state type to another state
     *
     * @param state         the state that will be mapped to another one (the receiving one)
     * @param itemMapper    the mapper between classes
     * @return State        the desired new state
     */
    fun <T, D > getStatesMapper(state: State<T>, itemMapper: (T) -> D) : State<D> {
        return when(state) {
            is State.Loading -> State.loading()
            is State.Success -> State.success(itemMapper(state.data))
            is State.Error -> State.error(state.errorMessage, state.errorViewData, state.error)
            is State.ErrorEmpty -> State.errorEmpty(state.errorMessage, state.errorMessageResId)
            is State.Default -> State.default()
            is State.LoadingRefresh -> State.loadingRefresh()
            is State.PagingError -> State.pagingError(state.message)
            is State.PagingSuccess -> State.pagingSuccess(itemMapper(state.data))
            is State.SignOut -> State.signOut(state.message)
            is State.UpdatePosition -> State.updatePosition(state.positionUpdate)
            else -> State.default()
        }
    }
}