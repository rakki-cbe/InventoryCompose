package com.example.pdfgenerator

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.os.Bundle
import android.util.Log
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.pdfgenerator.data.InventoryDB
import com.example.pdfgenerator.data.customer.Customer
import com.example.pdfgenerator.databinding.FragmentFirstBinding
import com.example.pdfgenerator.databinding.PdfViewSampleBinding
import com.example.pdfgenerator.viewmodel.BillerEntryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import java.io.File
import java.util.Date
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val userViewModel: BillerEntryViewModel by viewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            createTestPDF()
            //findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
        GlobalScope.launch {
            /*userViewModel.getAllCustomer().collect { it ->
                Log.d("Working", "Data collected " + it.size)
            }*/
        }

    }

    private fun createTestPDF() {

        /*GlobalScope.launch {
            userViewModel.setData().collect { it ->
                Log.d("Working", "Data Stored " + it)
            }
        }*/
        /**Generate Pdf Document**/
        val doc = PdfDocument()
        /**Generate Page Info For The Page**/
        val pageInfo = PdfDocument.PageInfo.Builder(PDF_PAGE_WIDTH, PDF_PAGE_HEIGHT, 1).create()
        val totalItem = 10
        /**Parent For SpendingItem's View**/
        val parentView = parentView()
        /**Header For the first page**/
        val pdfHeaderView = pdfHeaderView()
        /**Add Header on top in the parent**/
        parentView.addView(pdfHeaderView)
        /**Make First Page For Summary**/
        val page = doc.startPage(pageInfo)
        /**Render Page Fro First Page**/
        loadBitmapFromView(parentView)?.let {
            page.canvas.drawBitmap(
                it,
                0f,
                0f,
                null
            )
        }
        /**Finish the first page and remove all the views**/
        doc.finishPage(page)
        parentView.removeAllViews()
        val reportFolder = File(context?.filesDir, "report")
        if (!reportFolder.exists())
            reportFolder.mkdir()
        val file = File(
            reportFolder,
            "Report_test1.pdf"
        )
        doc.writeTo(file.outputStream())
        doc.close()
    }

    private fun parentView(): LinearLayout {
        val parentView = LinearLayout(context, null, R.style.Theme_PdfGenerator)
        val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        parentView.layoutParams = params
        parentView.orientation = LinearLayout.VERTICAL
        return parentView
    }


    private fun pdfHeaderView() =
        with(
            PdfViewSampleBinding.inflate(
                LayoutInflater.from(context)
                    .cloneInContext(ContextThemeWrapper(context, R.style.Theme_PdfGenerator))
            )
        ) {
//            dateRangeText.text = context.getString(
//                R.string.date_range,
//                DateUtil.getFormattedDate(startDate),
//                DateUtil.getFormattedDate(endDate)
//            )
//            this.totalAmount.text = context.getString(
//                R.string.rupee_symbol,
//                totalAmount.toString()
//            )
//            totalItem.text = totalItems.toString()
            this.root
        }

    private fun loadBitmapFromView(v: View): Bitmap? {
        val specWidth: Int =
            View.MeasureSpec.makeMeasureSpec(PDF_PAGE_WIDTH, View.MeasureSpec.EXACTLY)
        val specHeight: Int =
            View.MeasureSpec.makeMeasureSpec(PDF_PAGE_HEIGHT, View.MeasureSpec.EXACTLY)
        v.measure(specWidth, specHeight)
        val requiredWidth: Int = v.measuredWidth
        val requiredHeight: Int = v.measuredHeight
        val b = Bitmap.createBitmap(requiredWidth, requiredHeight, Bitmap.Config.ARGB_8888)
        val c = Canvas(b)
        c.drawColor(Color.WHITE)
        v.layout(v.left, v.top, v.right, v.bottom)
        v.draw(c)
        return b
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private  val PDF_PAGE_WIDTH = 595 //8.26 Inch
    private  val PDF_PAGE_HEIGHT = 842 //11.69 Inch

}