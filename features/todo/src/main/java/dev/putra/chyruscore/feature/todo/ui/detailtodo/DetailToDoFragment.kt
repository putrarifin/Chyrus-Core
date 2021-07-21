package dev.putra.chyruscore.feature.todo.ui.detailtodo

import dev.putra.chyruscore.feature.todo.databinding.FragmentDetailTodoBinding
import dev.putra.commons.ui.base.BaseFragment

class DetailToDoFragment : BaseFragment<FragmentDetailTodoBinding>() {

    override fun getLayoutBinding() = FragmentDetailTodoBinding.inflate(layoutInflater)

    override fun initialized() {
    }
}