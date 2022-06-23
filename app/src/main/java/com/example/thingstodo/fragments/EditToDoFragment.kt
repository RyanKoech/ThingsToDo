package com.example.thingstodo.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.thingstodo.databinding.FragmentEditToDoBinding

class EditToDoFragment : Fragment() {

    private var _binding : FragmentEditToDoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View? {

        _binding = FragmentEditToDoBinding.inflate(inflater, container,false)
        val view = binding.root

        return view
    }

    override fun onDestroy(){
        super.onDestroy()
        _binding = null
    }
}