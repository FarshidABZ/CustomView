package ordibehesht.customtoolbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by FarshidABZ.
 * Since 9/19/2017.
 */

public class CustomToolbar extends LinearLayout {

    private int backgroundSolidColor;
    private int backgroundDrawable;
    private float cornerRadius;
    private int startGradientColor;
    private int endGradientColor;
    private int gradientOrientation;
    private Context context;

    View customToolbarView;

    public CustomToolbar(Context context) {
        super(context);
        this.context = context;
        setToolbarSize();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomToolbar,
                0, 0);

        extractAttrs(typedArray);
        setBackGround();
        setToolbarSize();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomToolbar,
                0, 0);

        extractAttrs(typedArray);
        setBackGround();
        setToolbarSize();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.CustomToolbar,
                0, 0);

        extractAttrs(typedArray);
        setBackGround();
        typedArray.recycle();
        setToolbarSize();
    }

    private void extractAttrs(TypedArray typedArray) {
        try {
            backgroundSolidColor = typedArray.getColor(R.styleable.CustomToolbar_backgroundSolidColor, -1);
            backgroundDrawable = typedArray.getResourceId(R.styleable.CustomToolbar_backgroundDrawable, 0);
            cornerRadius = typedArray.getDimension(R.styleable.CustomToolbar_cornerRadius, 8);
            startGradientColor = typedArray.getColor(R.styleable.CustomToolbar_startGradientColor, 0);
            endGradientColor = typedArray.getColor(R.styleable.CustomToolbar_endGradientColor, 0);
            gradientOrientation = typedArray.getInteger(R.styleable.CustomToolbar_gradientOrientation, 0);

            Log.e(">>>>>", "backgroundSolidColor " + backgroundSolidColor);
            Log.e(">>>>>", "backgroundDrawable " + backgroundDrawable);
            Log.e(">>>>>", "cornerRadius " + cornerRadius);
            Log.e(">>>>>", "startGradientColor " + startGradientColor);
            Log.e(">>>>>", "endGradientColor " + endGradientColor);
            Log.e(">>>>>", "gradientOrientation " + gradientOrientation);

        } finally {
            typedArray.recycle();
        }
    }

    private void setBackGround() {
        if (backgroundDrawable != 0) {
            Drawable drawable = ContextCompat.getDrawable(context, backgroundDrawable);
            this.setBackground(drawable);
        } else {
            GradientDrawable gradientDrawable = getGradientDrawable();
            gradientDrawable.setCornerRadius(cornerRadius);
            gradientDrawable.setCornerRadii(new float[]{0, 0, 0, 0, cornerRadius, cornerRadius, cornerRadius, cornerRadius});
            setBackgroundColor(backgroundSolidColor);
            this.setBackground(gradientDrawable);
        }
    }

    private GradientDrawable getGradientDrawable() {
        GradientDrawable gradientDrawable;
        if (backgroundSolidColor != -1) {
            gradientDrawable = new GradientDrawable(getGradientOrientation(),
                    new int[]{backgroundSolidColor, backgroundSolidColor});
        } else if (startGradientColor != 0 || endGradientColor != 0) {
            gradientDrawable = new GradientDrawable(getGradientOrientation(),
                    new int[]{startGradientColor, endGradientColor});
        } else {
            int defColor = ContextCompat.getColor(context, R.color.colorPrimary);
            gradientDrawable = new GradientDrawable(getGradientOrientation(),
                    new int[]{defColor, defColor});
        }

        return gradientDrawable;
    }

    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    private GradientDrawable.Orientation getGradientOrientation() {
        switch (gradientOrientation) {
            case 0:
                return GradientDrawable.Orientation.LEFT_RIGHT;
            case 1:
                return GradientDrawable.Orientation.RIGHT_LEFT;
            case 2:
                return GradientDrawable.Orientation.TOP_BOTTOM;
            case 3:
                return GradientDrawable.Orientation.BOTTOM_TOP;
            default:
                return GradientDrawable.Orientation.LEFT_RIGHT;
        }
    }

    private void resizeView(int newHeight) {
        LinearLayout.LayoutParams layoutParams = (LayoutParams) customToolbarView.getLayoutParams();
        layoutParams.height = newHeight;
        customToolbarView.setLayoutParams(layoutParams);
    }

    private void setToolbarSize() {
        inflate(context, R.layout.custom_title, this);
        customToolbarView = findViewById(R.id.customToolbarView);

        resizeView(getResources().getDimensionPixelSize(R.dimen.actionBarSize));
    }
}
