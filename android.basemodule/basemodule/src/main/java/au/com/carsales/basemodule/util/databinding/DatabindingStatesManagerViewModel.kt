package au.com.carsales.basemodule.util.databinding

import au.com.carsales.basemodule.util.orFalse
import au.com.carsales.basemodule.util.viewModel.BaseViewModel

/**
 * Created by Dan on 23, March, 2020
 * Copyright (c) 2020 Carsales. All rights reserved.
 *
 * Viewmodel that extends from base viewmodel
 * and has the ability to manage more than one
 * object of states (loading, empty, hasContent, etc..)
 */
abstract class DatabindingStatesManagerViewModel : BaseViewModel() {

    val databindingStates : HashMap<String, DatabindingState>? = null

    fun setShowLoading() {
        isLoading.set(true)
        isRefreshing.set(false)
        isError.set(false)
        isEmpty.set(false)

        setDatabindingManagersLoadingState(true)
        setDatabindingManagersErrorState(false)
        setDatabindingManagersEmptyState(false)
        setDatabindingManagersRefreshingState(false)
    }

    override fun setShowError() {
        isLoading.set(false)
        isRefreshing.set(false)
        isError.set(true)
        isEmpty.set(false)

        setDatabindingManagersErrorState(true)
        setDatabindingManagersEmptyState(false)
        setDatabindingManagersLoadingState(false)
        setDatabindingManagersRefreshingState(false)
    }

    override fun setShowEmpty() {
        isLoading.set(false)
        isRefreshing.set(false)
        isError.set(false)
        isEmpty.set(true)

        setDatabindingManagersEmptyState(true)
        setDatabindingManagersLoadingState(false)
        setDatabindingManagersRefreshingState(false)
        setDatabindingManagersErrorState(false)
    }

    override fun setShowContent() {
        isLoading.set(false)
        isRefreshing.set(false)
        isError.set(false)
        isEmpty.set(false)

        setDatabindingManagersLoadingState(false)
        setDatabindingManagersRefreshingState(false)
        setDatabindingManagersErrorState(false)
        setDatabindingManagersEmptyState(false)
    }

    /**
     * Adds a new state only if the tag
     * param doesn't exist in the actual list
     *
     * @param tag   the tag value to search in the hashmap
     * @return      a existent state or a new one depending the case
     */
    fun addNewState(tag: String) : DatabindingState {
        val tagExists = databindingStates?.containsKey(tag).orFalse()

        return if(tagExists) {
            databindingStates?.get(tag)!!
        } else {
            databindingStates?.put(tag, DatabindingState())!!
        }
    }

    /**
     * Sets a new Loading state to
     * all state objects from the list
     *
     * @param newState      the new state to be set
     */
    fun setDatabindingManagersLoadingState(newState : Boolean) {
        databindingStates?.values?.forEach {
            it.isLoading.set(newState)
        }
    }

    /**
     * Sets a new Loading state to
     * all state objects from the list
     *
     * @param newState      the new state to be set
     */
    fun setDatabindingManagersErrorState(newState : Boolean) {
        databindingStates?.values?.forEach {
            it.isError.set(newState)
        }
    }

    /**
     * Sets a new Empty state to
     * all state objects from the list
     *
     * @param newState      the new state to be set
     */
    fun setDatabindingManagersEmptyState(newState : Boolean) {
        databindingStates?.values?.forEach {
            it.isEmpty.set(newState)
        }
    }

    /**
     * Sets a new Refreshing state to
     * all state objects from the list
     *
     * @param newState      the new state to be set
     */
    fun setDatabindingManagersRefreshingState(newState : Boolean) {
        databindingStates?.values?.forEach {
            it.isRefreshing.set(newState)
        }
    }

    /**
     * Sets a new HasContent state to
     * all state objects from the list
     *
     * @param newState      the new state to be set
     */
    fun setDatabindingManagersHasContentState(newState : Boolean) {
        databindingStates?.values?.forEach {
            it.hasContent.set(newState)
        }
    }
}