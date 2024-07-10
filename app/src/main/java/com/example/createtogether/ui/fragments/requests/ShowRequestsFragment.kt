package com.example.createtogether.ui.fragments.requests

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.createtogether.R
import com.example.createtogether.adapters.RequestItemClickInterface
import com.example.createtogether.adapters.RequestsAdapter
import com.example.createtogether.databinding.FragmentShowRequestsBinding
import com.example.createtogether.db.models.Request
import com.example.createtogether.utility.UserUtil
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShowRequestsFragment : Fragment(R.layout.fragment_show_requests), RequestItemClickInterface {

    private lateinit var binding: FragmentShowRequestsBinding

    private val databaseReference = FirebaseDatabase.getInstance().reference
    private val requestsReference = databaseReference.child("requests")

//    private val viewModel: ViewModel by viewModels()
    private lateinit var requestsAdapter: RequestsAdapter
    private val requests = mutableListOf<Request>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentShowRequestsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        val userId = UserUtil.getUserId(requireActivity())  //checken ob das probleme macht da ja rückgabewert nicht verarbeitet wird
        val requestReferenceUser = requestsReference.child(userId)

        requestReferenceUser.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                requests.clear()

                for (request in dataSnapshot.children) {
//                    val requestId = request.child("requestId").getValue(String::class.java) ?: "Not found"
                    val requestId = request.child("requestId").getValue(String::class.java) ?: "Not found"
                    val requestStatus = request.child("requestStatus").getValue(String::class.java) ?: "Not found"
                    val requestCreator = request.child("requestCreator").getValue(String::class.java) ?: "Not found"
                    val textId = request.child("textId").getValue(String::class.java) ?: "Not found"
                    val modifiedTextTitle = request.child("modifiedTextTitle").getValue(String::class.java) ?: "Not found"
                    val modifiedText = request.child("modifiedText").getValue(String::class.java) ?: "Not found"

                    val request = Request(requestId, requestStatus, requestCreator, textId, modifiedTextTitle, modifiedText)

                    requests.add(request)



//
//                    Log.d("HeyJa", textId)
//                    Log.d("HeyJa", modifiedTextTitle)
//
                    // Hier wird getTextById mit der Lifecycle-Owner der MainActivity aufgerufen
//                    viewModel.getTextById(textId).observe(requireActivity(), Observer { textContent ->
//                        if (textContent != null) {
//                            Log.d("HeyJa", textContent.creator)
//                            Log.d("HeyJa", textContent.category)
//                        }
//
//                    })

                }

                if(requests.isEmpty()) {
                    binding.textViewNoRequests.visibility = View.VISIBLE
                } else {

                    val sortedRequests = requests.sortedBy { it.requestStatus != "Waiting for review" }
                    requestsAdapter.updateListOfRequests(sortedRequests)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
    }

    private fun setupRecyclerView() = binding.recyclerViewRequests.apply {
        requestsAdapter = RequestsAdapter(this@ShowRequestsFragment)
        adapter = requestsAdapter
        layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onClick(request: Request) {
        Log.d("Test", request.modifiedTextTitle)

//        if(request.requestStatus == "Accepted" || request.requestStatus == "Declined") {
//            Toast.makeText(requireContext(), "You already reviewed this text", Toast.LENGTH_LONG).show()
//        } else {
//            val action = ShowRequestsFragmentDirections.actionShowRequestsFragmentToViewChangesRequestFragment(request)
//            findNavController().navigate(action)
//        }
        val action = ShowRequestsFragmentDirections.actionShowRequestsFragmentToViewChangesRequestFragment(request)
        findNavController().navigate(action)
    }

}