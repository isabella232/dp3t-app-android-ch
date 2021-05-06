package ch.admin.bag.dp3t.inform

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ch.admin.bag.dp3t.R
import ch.admin.bag.dp3t.databinding.FragmentShareCheckinsBinding
import ch.admin.bag.dp3t.inform.models.Status
import ch.admin.bag.dp3t.util.showFragment

class ShareCheckinsFragment : Fragment() {

	companion object {
		@JvmStatic
		fun newInstance() = ShareCheckinsFragment()
	}

	private val informViewModel: InformViewModel by activityViewModels()

	private lateinit var binding: FragmentShareCheckinsBinding

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentShareCheckinsBinding.inflate(inflater).apply {
			val adapter = CheckinAdapter()
			checkinsRecyclerView.adapter = adapter
			checkinsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
			adapter.setData(informViewModel.selectableDiaryItems)
			adapter.itemSelectionListener { selectedItem, selected ->
				informViewModel.selectableDiaryItems.find { it.diaryEntry == selectedItem }?.isSelected = selected
			}
			selectAllCheckbox.setOnCheckedChangeListener { _, isChecked ->
				informViewModel.selectableDiaryItems.forEach { it.isSelected = isChecked }
				adapter.setData(informViewModel.selectableDiaryItems)
			}
			dontSendButton.paintFlags = dontSendButton.paintFlags or Paint.UNDERLINE_TEXT_FLAG
			dontSendButton.setOnClickListener {

			}
			sendButton.setOnClickListener {
				informViewModel.userUpload().observe(viewLifecycleOwner) {
					when (it.status) {
						Status.LOADING -> {
							//TODO: Show loading animation
						}
						Status.ERROR -> {
							//TODO: Handle error
						}
						Status.SUCCESS -> {
							showFragment(ThankYouFragment.newInstance(), R.id.inform_fragment_container)
						}
					}
				}
			}
		}
		return binding.root
	}

}