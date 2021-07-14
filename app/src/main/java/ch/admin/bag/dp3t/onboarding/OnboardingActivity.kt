/*
 * Copyright (c) 2020 Ubique Innovation AG <https://www.ubique.ch>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 *
 * SPDX-License-Identifier: MPL-2.0
 */
package ch.admin.bag.dp3t.onboarding

import android.animation.Animator
import android.content.Intent
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import ch.admin.bag.dp3t.R
import org.dpppt.android.sdk.DP3T
import java.util.*

private const val SHOW_SPLASHBOARDING_MILLIS = 3000

class OnboardingActivity : FragmentActivity() {

	companion object {
		const val ARG_ONBOARDING_TYPE = "ARG_ONBOARDING_TYPE"
		const val ARG_INSTANT_APP_URL = "ARG_INSTANT_APP_URL"
	}

	private lateinit var splashboarding: View
	private lateinit var viewPager: ViewPager2
	private lateinit var pagerAdapter: FragmentStateAdapter

	private fun isGmsInstalled(): Boolean {
		val pm = this.packageManager;

		for (pi in pm.getInstalledPackages(0)) {
			if (pi.packageName.equals("com.google.android.gms")) {
				return true;
			}
		}

		return false;
	}

	private fun checkGms() {
		val TAG = "NoGAEN";
		var titleId: Int;
		var messageId: Int;

		if (isGmsInstalled()) {
			Log.d(TAG, "GMS is installed on device")
			titleId = R.string.nogaenalert_title_gms_on_device
			messageId = R.string.nogaenalert_msg_gms_on_device
		} else {
			Log.d(TAG, "GMS is not installed on device")
			titleId = R.string.nogaenalert_title_no_gms
			messageId = R.string.nogaenalert_msg_no_gms
		}

		val dialog: AlertDialog = AlertDialog.Builder(this, R.style.NextStep_AlertDialogStyle)
			.setTitle(titleId)
			.setMessage(messageId)
			.setIcon(R.drawable.ic_warning_red)
			.setCancelable(false)
			.setPositiveButton(R.string.nogaenalert_positive_btn, null)
			.setNegativeButton(R.string.nogaenalert_negative_btn) { view, arg -> finishAndRemoveTask() }
			.create()
		dialog.show()
		// Make the links clickable
		(Objects.requireNonNull<Any?>(dialog.findViewById(android.R.id.message)) as TextView).movementMethod =
			LinkMovementMethod.getInstance()
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_onboarding)

		checkGms();

		splashboarding = findViewById(R.id.splashboarding)
		viewPager = findViewById(R.id.pager)

		viewPager.isUserInputEnabled = false
		val onboardingType = intent.getSerializableExtra(ARG_ONBOARDING_TYPE) as OnboardingType
		pagerAdapter = OnboardingSlidePageAdapter(this, onboardingType)
		viewPager.adapter = pagerAdapter
		if (onboardingType == OnboardingType.NORMAL) {
			splashboarding.postDelayed({
				splashboarding.alpha = 1f
				splashboarding.animate().alpha(0f).setDuration(200).withLayer().setListener(object : Animator.AnimatorListener {
					override fun onAnimationStart(animator: Animator) {}
					override fun onAnimationEnd(animator: Animator) {
						splashboarding.visibility = View.GONE
					}

					override fun onAnimationCancel(animator: Animator) {}
					override fun onAnimationRepeat(animator: Animator) {}
				}).start()
			}, SHOW_SPLASHBOARDING_MILLIS.toLong())
		} else {
			splashboarding.alpha = 0f
		}
	}

	fun continueToNextPage() {
		val currentItem = viewPager.currentItem
		if (currentItem < pagerAdapter.itemCount - 1) {
			viewPager.setCurrentItem(currentItem + 1, true)
		} else {
			val resultIntent = Intent()
				.putExtra(ARG_ONBOARDING_TYPE, intent.getSerializableExtra(ARG_ONBOARDING_TYPE))
				.putExtra(ARG_INSTANT_APP_URL, intent.getStringExtra(ARG_INSTANT_APP_URL))
			setResult(RESULT_OK, resultIntent)
			finish()
			overridePendingTransition(R.anim.fragment_open_enter, R.anim.fragment_open_exit)
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		DP3T.onActivityResult(this, requestCode, resultCode, data)
	}

}