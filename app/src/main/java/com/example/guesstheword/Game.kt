package com.example.guesstheword

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.guesstheword.databinding.FragmentGameBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Game.newInstance] factory method to
 * create an instance of this fragment.
 */
class Game : Fragment() {
    // TODO: Rename and change types of parameters


    private lateinit var binding: FragmentGameBinding
    private lateinit var viewModel: GameViewModel





    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i("GameFragment", "Called ViewModelProvider")
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)


        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        binding.gameViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner


        viewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if(hasFinished) gameFinished()
        })





        return binding.root
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_game, container, false)
    }



    private fun gameFinished() {
        Toast.makeText(activity, "Game has finished!", Toast.LENGTH_SHORT).show()
        val action = GameDirections.actionGameToScoreFragment()
        action.score = viewModel.score.value?:0
       NavHostFragment.findNavController(this).navigate(action)
        viewModel.onGameCompleted()

    }

}