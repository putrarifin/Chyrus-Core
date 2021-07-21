package dev.putra.chyruscore.feature.todo.ui.addtodo

import dev.putra.chyruscore.feature.todo.databinding.FragmentAddTodoBinding
import dev.putra.commons.ui.base.BaseBottomSheetFragment

class AddTodoDialogFragment : BaseBottomSheetFragment<FragmentAddTodoBinding>() {

    override fun getLayoutBinding() = FragmentAddTodoBinding.inflate(layoutInflater)

    override fun initialized() {
    }
}