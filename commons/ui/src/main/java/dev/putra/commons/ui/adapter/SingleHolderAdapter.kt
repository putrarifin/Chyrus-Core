package dev.putra.commons.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.Delegates

@Suppress("UNCHECKED_CAST")
abstract class SingleHolderAdapter<Binding : ViewBinding, Model : Any>
    : RecyclerView.Adapter<BindableViewHolder<Binding>>() {

    private var _itemCollection: List<Model> by Delegates.observable(arrayListOf()) { _, oldList, newList ->
        autoCalculateDiff(oldList, newList)
    }

    val itemCollection get() = _itemCollection

    private var itemCollectionMap: LinkedHashMap<Any, Model> = linkedMapOf()
        set(value) {
            field = value
            _itemCollection = value.values.toList()
        }

    fun setNewCollection(vararg newList: Model) {
        val newCollection: LinkedHashMap<Any, Model> = linkedMapOf()
        newList.forEach {
            newCollection[it.identifier()] = it
        }
        itemCollectionMap = newCollection
    }

    fun setNewCollection(newList: List<Model>) {
        val newCollection: LinkedHashMap<Any, Model> = linkedMapOf()
        newList.forEach {
            newCollection[it.identifier()] = it
        }
        itemCollectionMap = newCollection
    }

    fun setNewCollection(newList: LinkedHashMap<Any, Model>) {
        itemCollectionMap = newList
    }

    fun addItemsToCollection(items: List<Model>) {
        val tempCollectionMap = LinkedHashMap(itemCollectionMap)
        items.forEach {
            tempCollectionMap[it.identifier()] = it
        }
        itemCollectionMap = tempCollectionMap
    }

    fun addItemsToCollection(vararg items: Model) {
        val tempCollectionMap = LinkedHashMap(itemCollectionMap)
        items.forEach {
            tempCollectionMap[it.identifier()] = it
        }
        itemCollectionMap = tempCollectionMap
    }

    fun clearCollection() {
        itemCollectionMap = linkedMapOf()
    }

    private fun autoCalculateDiff(oldList: List<Model>, newList: List<Model>) {
        val diff = DiffUtil.calculateDiff(object : DiffUtil.Callback() {

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].identifier() == newList[newItemPosition].identifier()
            }

            override fun getOldListSize(): Int {
                return oldList.size
            }

            override fun getNewListSize(): Int {
                return newList.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition].contentIdentifier() == newList[newItemPosition].contentIdentifier()
            }
        })
        diff.dispatchUpdatesTo(this)
    }

    abstract fun Model.identifier(): Any

    open fun Model.contentIdentifier(): Any = identifier()

    abstract fun createViewBinding(inflater: LayoutInflater, parent: ViewGroup): Binding

    abstract fun bindView(binding: Binding, item: Model)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindableViewHolder<Binding> {
        return getViewHolder(parent)
    }

    private var listenerWithPayload: ((Model, Any?) -> Unit)? = null
    private var listener: ((Model) -> Unit)? = null

    fun sendEvent(model: Model, payload: Any? = null) {
        listenerWithPayload?.invoke(model, payload)
    }

    fun setOnEventListener(callback: (Model, Any?) -> Unit) {
        listenerWithPayload = callback
    }

    fun sendEvent(model: Model) {
        listener?.invoke(model)
    }

    fun setOnEventListener(callback: (Model) -> Unit) {
        listener = callback
    }

    override fun getItemCount(): Int {
        return _itemCollection.size
    }

    override fun onBindViewHolder(holder: BindableViewHolder<Binding>, position: Int) {
        bindView(holder.binding, _itemCollection[position])
    }

    private fun getViewHolder(parent: ViewGroup): BindableViewHolder<Binding> {
        return BindableViewHolder(createViewBinding(LayoutInflater.from(parent.context), parent))
    }

}