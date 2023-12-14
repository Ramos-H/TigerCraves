package com.itg3.grp1.tigercraves

import android.content.Context
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView

class ValEditText : LinearLayout
{
    var text: String?
        get() = textField.text.toString()
        set(value) = textField.setText(value)

    var error: String?
        get() = validationText.text.toString()
        set(value)
        {
            textField.error = value
            validationText.text = value

            if(value != null)
            {
                textField.setTextColor(resources.getColor(R.color.errorText))
                validationText.visibility = View.VISIBLE
            }
            else
            {
                textField.setTextColor(resources.getColor(R.color.defaultText))
                validationText.visibility = View.INVISIBLE
            }

            invalidate()
            requestLayout()
        }

    var hint: String?
        get() = textField.hint.toString()
        set(value)
        {
            textField.hint = value
            invalidate()
            requestLayout()
        }

    var inputType: Int
        get() = textField.inputType
        set(value)
        {
            textField.inputType = value
            invalidate()
            requestLayout()
        }

    var lines: Int
        get() = textField.lineCount
        set(value)
        {
            textField.setLines(value)
            invalidate()
            requestLayout()
        }

    var maxLines: Int
        get() = textField.maxLines
        set(value)
        {
            textField.maxLines = value
            invalidate()
            requestLayout()
        }

    var maxLength: Int
        get()
        {
            val lengthFilter = textField.filters.filter { it is InputFilter.LengthFilter }
                .firstOrNull() as InputFilter.LengthFilter
            return lengthFilter.max
        }
        set(value)
        {
            val newFilters = textField.filters.filterNot { it is InputFilter.LengthFilter }.toMutableList()
            if(value > -1)
            {
                newFilters.add(InputFilter.LengthFilter(value))
            }
            textField.filters = newFilters.toTypedArray()
            invalidate()
            requestLayout()
        }

    private lateinit var textField: EditText
    private lateinit var validationText: TextView

    constructor(context: Context) : super(context)
    {
        initControl()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    {
        initControl()
        context.theme.obtainStyledAttributes(attrs,
            R.styleable.ValEditText,
            0, 0).apply {
            try
            {
                text = getString(R.styleable.ValEditText_android_text)
                hint = getString(R.styleable.ValEditText_android_hint)
                inputType = getInteger(R.styleable.ValEditText_android_inputType, InputType.TYPE_CLASS_TEXT)
                lines = getInteger(R.styleable.ValEditText_android_lines, 1)
                maxLines = getInteger(R.styleable.ValEditText_android_maxLines, 1)
                maxLength = getInteger(R.styleable.ValEditText_android_maxLength, -1)
                invalidate()
                requestLayout()
            }
            finally
            {
                recycle()
            }
        }
    }

    private fun initControl()
    {
        val view = inflate(context, R.layout.control_val_edit_text, this)

        textField = view.findViewById(R.id.textField)
        validationText = view.findViewById(R.id.validationText)
    }
}