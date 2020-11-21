package com.example.coachticketbooking.screen.ticket

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.coachticketbooking.R

class PreviewTicketFragment : Fragment() {

    companion object {
        fun newInstance() = PreviewTicketFragment()
    }

    private lateinit var viewModel: PreviewTicketViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.preview_ticket_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PreviewTicketViewModel::class.java)
        // TODO: Use the ViewModel
    }

}