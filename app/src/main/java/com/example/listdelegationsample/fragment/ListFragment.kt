package com.example.listdelegationsample.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.listdelegationsample.adapter.TextFieldDelegationAdapter
import com.example.listdelegationsample.adapter.TextFieldModel
import com.example.listdelegationsample.databinding.ListFragmentBinding
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter

class ListFragment: Fragment() {

    //Тут мы создаем вью из объекта верстки при помощи ViewBinding, с ним уже тоже знакомы
    private lateinit var binding: ListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //* Тут мы задаем нашему recyclerView лист из адаптеров, для каждого уникального элемента
        // создается собственный адаптер, это позволяет не модифицировать каждый раз код старого адаптера
        // а просто добавлять новый под нужды
        binding.apply {
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