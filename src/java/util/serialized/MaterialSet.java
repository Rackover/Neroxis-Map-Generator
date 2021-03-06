package util.serialized;

import java.util.List;

/** Used in disk operations to be converted into a material later */
public strictfp class MaterialSet {
    public String name;
    public List<Material> materials;
    public MaterialElement macroTexture;

    public strictfp class Material {
        public MaterialElement texture;
        public MaterialElement normal;
    }

    public strictfp class MaterialElement {
        public String environment;
        public String name;
        public float scale;
    }
}