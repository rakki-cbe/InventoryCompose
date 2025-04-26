package com.example.pdfgenerator.ui.pdfgenerator

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.pdf.PdfDocument
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.example.pdfgenerator.R
import com.example.pdfgenerator.data.model.InvoiceLineItemForPrint
import com.example.pdfgenerator.data.model.InvoiceUserDisplayData
import com.example.pdfgenerator.data.utils.NumberToWordsConverter
import com.example.pdfgenerator.databinding.InvoiceViewBaseBinding
import com.example.pdfgenerator.databinding.PrinterItemRowBinding
import com.example.pdfgenerator.extension.convertToDouble
import java.io.File

private val PDF_PAGE_WIDTH = 595 //8.26 Inch
private val PDF_PAGE_HEIGHT = 842 //11.69 Inch
fun createPDF(
    context: Context,
    invoiceData: InvoiceUserDisplayData,
    lineItem: List<InvoiceLineItemForPrint>
): String {
    val infalter = LayoutInflater.from(context)
        .cloneInContext(ContextThemeWrapper(context, R.style.Theme_PdfGenerator))

    /**Generate Pdf Document**/
    val doc = PdfDocument()

    /**Generate Page Info For The Page**/
    val pageInfo = PdfDocument.PageInfo.Builder(PDF_PAGE_WIDTH, PDF_PAGE_HEIGHT, 1).create()
    val totalItem = 10

    /**Parent For SpendingItem's View**/
    val parentView = parentView(context)

    /**Header For the first page**/
    val pdfHeaderView = pdfHeaderView(context, infalter, invoiceData, lineItem)
    /**Add Header on top in the parent**/
    parentView.addView(pdfHeaderView)
    /**Make First Page For Summary**/
    val page = doc.startPage(pageInfo)
    /**Render Page Fro First Page**/
    loadBitmapFromView(parentView).let {
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
    val reportFolder = File(context.filesDir, "report")
    if (!reportFolder.exists())
        reportFolder.mkdir()
    val file = File(
        reportFolder,
        invoiceData.billId.toString() + ".pdf"
    )
    val path = file.path
    doc.writeTo(file.outputStream())
    doc.close()
    return path
}

private fun parentView(context: Context): LinearLayout {
    val parentView = LinearLayout(context, null, R.style.Theme_PdfGenerator)
    val params: ViewGroup.LayoutParams = ViewGroup.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.MATCH_PARENT
    )
    parentView.layoutParams = params
    parentView.orientation = LinearLayout.VERTICAL
    return parentView
}


private fun pdfHeaderView(
    context: Context,
    infalter: LayoutInflater,
    invoiceData: InvoiceUserDisplayData,
    lineItem: List<InvoiceLineItemForPrint>
): View {
    val mainBinding = InvoiceViewBaseBinding.inflate(infalter)
    mainBinding.apply {
        val finalAmountInDouble = invoiceData.finalAmount.convertToDouble(0.0)
        this.companyDetails.companyName.text = invoiceData.companyName
        this.companyDetails.companyAddress.text = invoiceData.address
        this.companyDetails.companyGst.text =
            String.format(context.getString(R.string.biller_gst), invoiceData.gst)
        this.companyDetails.companyPhoneNumber.text =
            String.format(context.getString(R.string.biller_phone), invoiceData.phoneNumber)
        this.companyDetails.companyEmail.text =
            String.format(context.getString(R.string.biller_email), invoiceData.email)
        this.customer.companyName.text = invoiceData.custCompanyName
        this.customer.companyAddress.text = invoiceData.custAddress
        this.customer.companyGst.text =
            String.format(context.getString(R.string.biller_gst), invoiceData.custGst)
        this.customer.companyPhoneNumber.text =
            String.format(context.getString(R.string.biller_phone), invoiceData.custPhoneNumber)
        this.customer.billDate.text = invoiceData.date
        this.customer.billId.text = invoiceData.billId.toString()
        this.customer.companyId.text = invoiceData.companyId.toString()
        this.items.totalFinalAmount.text = invoiceData.finalAmount
        this.customerBank.companyBankName.text = invoiceData.bankName
        this.customerBank.companyBankAcc.text = invoiceData.accountNumber
        this.customerBank.companyBankIfsc.text = invoiceData.ifscCode
        this.items.amountWords.text = NumberToWordsConverter.convert(finalAmountInDouble.toInt())
        loadInventoryItem(context, infalter, lineItem, this.items.content)
        this.enterpriseName.text = String.format(
            context.getString(R.string.biller_label_enterprice_name),
            invoiceData.companyName
        )
    }
    return mainBinding.root
}

fun loadInventoryItem(
    context: Context,
    infalter: LayoutInflater,
    invoiceLineItems: List<InvoiceLineItemForPrint>,
    contentView: LinearLayout,
) {
    invoiceLineItems.forEach {
        var item = PrinterItemRowBinding.inflate(infalter)
        item.itemNo.text = it.itemId.toString()
        item.qty.text = it.numberOfItem
        item.code.text = it.itemCode.toString()
        item.desc.text = it.itemName
        item.price.text =
            (it.unitPrice.convertToDouble(0.0) - it.discount.convertToDouble(0.0)).toString()
        item.gst.text = String.format(
            context.getString(R.string.biller_gst_desc), it.cgst + "%",
            it.sgst + "%", it.igst + "%"
        )
        contentView.addView(item.root)
        item.totalUnitAmount.text = it.totalAmountWithOutGst
        item.gstTotal.text = it.totalGstAmount

    }
}


private fun loadBitmapFromView(v: View): Bitmap {
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