package com.smapps.agenda.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.smapps.agenda.R;

public class HeaderComponent extends ConstraintLayout {

    private ConstraintLayout constraintLayout;

    private ImageView iconeLeft;
    private TextView titre;
    private ImageView icone;

    public HeaderComponent(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initComponent(context, attrs);
    }

    public HeaderComponent(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initComponent(context, attrs);
    }

    private void initComponent(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.header_component_layout, this);

        this.constraintLayout = findViewById(R.id.constraint);
        this.iconeLeft = findViewById(R.id.icone_left);
        this.titre = findViewById(R.id.titre);
        this.icone = findViewById(R.id.icone);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.HeaderComponent);

        int couleur = typedArray.getColor(R.styleable.HeaderComponent_couleurFond, getResources().getColor(R.color.blanc, null));
        this.constraintLayout.setBackgroundColor(couleur);

        this.titre.setText(typedArray.getString(R.styleable.HeaderComponent_titre));

        Drawable icone = typedArray.getDrawable(R.styleable.HeaderComponent_icone);
        if (icone != null) {
            this.icone.setVisibility(View.VISIBLE);
            this.icone.setImageDrawable(icone);
        } else {
            this.icone.setVisibility(View.GONE);
        }

        boolean displayIconeLeft = typedArray.getBoolean(R.styleable.HeaderComponent_afficherIconeLeft, false);
        if (!displayIconeLeft) {
            this.iconeLeft.setVisibility(View.GONE);
        } else {
            this.iconeLeft.setVisibility(View.VISIBLE);
        }

        typedArray.recycle();

        this.icone.setOnClickListener((v) -> {
            Toast.makeText(context, "test", Toast.LENGTH_SHORT).show();
        });
    }

    public void setTitre(String titre) {
        if (!TextUtils.isEmpty(titre)) {
            this.titre.setText(titre);
        }
    }

    public void setOnIconeClickListenenr(View.OnClickListener onClickListener) {
        this.icone.setOnClickListener(onClickListener);
    }

    public void setOnIconeLeftClickListenenr(View.OnClickListener onClickListener) {
        this.iconeLeft.setOnClickListener(onClickListener);
    }
}
