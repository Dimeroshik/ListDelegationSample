package com.example.listdelegationsample.ui.fragment.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.listdelegationsample.databinding.ListFragmentBinding
import com.example.listdelegationsample.ui.adapter.TextFieldDelegationAdapter
import com.example.listdelegationsample.ui.adapter.TextFieldModel
import com.example.listdelegationsample.ui.common.BaseFragment

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ListFragment: BaseFragment<ListFragmentBinding>() {

    //Этот метод позволяет просто создать binding и передать его в BaseFragment, это сделано для
    //удобства, ведь binding мы создаем в каждом фрагменте
    override fun onViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): ListFragmentBinding = ListFragmentBinding.inflate(inflater)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //* Тут мы задаем нашему recyclerView лист из адаптеров, для каждого уникального элемента
        // создается собственный адаптер, это позволяет не модифицировать каждый раз код старого адаптера
        // а просто добавлять новый под нужды
        binding?.apply {
            rvRoot.adapter = ListDelegationAdapter(
                TextFieldDelegationAdapter(
                    getString = {
                        getString(it)
                    }
                )
            ).apply {
                // функция apply позволяет работать в пространстве внутри объекта поэтому можем сразу
                // обратиться к items
                items = getItemsList()
            }
        }
    }

    private fun getItemsList(count: Int = 20): MutableList<TextFieldModel>{
        var list: MutableList<TextFieldModel> = mutableListOf()

        (0..count).onEach {
            list.add(TextFieldModel(it))
        }

        return list
    }


}