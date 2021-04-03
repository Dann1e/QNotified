/*
 * QNotified - An Xposed module for QQ/TIM
 * Copyright (C) 2019-2021 dmca@ioctl.cc
 * https://github.com/ferredoxin/QNotified
 *
 * This software is non-free but opensource software: you can redistribute it
 * and/or modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either
 * version 3 of the License, or any later version and our eula as published
 * by ferredoxin.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * and eula along with this software.  If not, see
 * <https://www.gnu.org/licenses/>
 * <https://github.com/ferredoxin/QNotified/blob/master/LICENSE.md>.
 */

package me.singleneuron.qn_kernel.ui

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import me.singleneuron.qn_kernel.ui.base.UiCategory
import me.singleneuron.qn_kernel.ui.base.UiGroup
import me.singleneuron.qn_kernel.ui.base.UiItem
import me.singleneuron.qn_kernel.ui.base.UiScreen
import nil.nadph.qnotified.ui.ViewBuilder.newListItemButton
import nil.nadph.qnotified.ui.ViewBuilder.subtitle
import nil.nadph.qnotified.util.Utils

class SettingsFragment : Fragment() {

    private lateinit var uiScreen: UiScreen

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val ll = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        addViewInUiGroup(uiScreen, ll)

        return ll
    }

    fun setUiScreen(uiScreen: UiScreen): SettingsFragment {
        this.uiScreen = uiScreen
        return this
    }

    private fun addViewInUiGroup(uiGroup: UiGroup, viewGroup: ViewGroup) {
        for (uiDescription in uiGroup.contains.values) {
            Utils.logd("Adding: $uiDescription")
            if (uiDescription is UiCategory) {
                viewGroup.addView(subtitle(activity, uiDescription.name))
                addViewInUiGroup(uiDescription, viewGroup)
            } else if (uiDescription is UiItem) {
                try {
                    viewGroup.addView(newListItemButton(activity, uiDescription.preference.title, uiDescription.preference.summary, null) {
                        uiDescription.preference.onPreferenceClickListener.onPreferenceClick(null)
                    })
                } catch (e:Exception) {
                    Utils.log(e)
                }
            }
        }
    }

}
