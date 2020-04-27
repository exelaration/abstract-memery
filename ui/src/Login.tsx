import React from "react";
import "./SignUp.css";
import axios from "axios";
import { useFormik } from "formik";
import * as Yup from "yup";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import FormControl from "react-bootstrap/FormControl";
import { useCookies } from "react-cookie";
import { useHistory } from "react-router-dom";

const validationSchema = Yup.object({
  username: Yup.string().email().max(30).required("Required"),
  password: Yup.string().required("Required"),
});

function Login() {
  // eslint-disable-next-line
  const [cookies, setCookie] = useCookies(["userToken"]);
  const history = useHistory();
  const { handleSubmit, handleChange, values, errors } = useFormik({
    initialValues: {
      username: "",
      password: "",
    },
    validationSchema,
    onSubmit(values) {
      axios
        .post("http://localhost:8080/login", {
          username: values.username,
          password: values.password,
        })
        .then((res) => {
          setCookie("userToken", res.headers["authorization"], { path: "/" });
          history.push("/");
        })
        .catch((error) => {
          alert("Unable to log in. Please check your username and password");
        });
    },
  });

  return (
    <div className="signup">
      <header className="signupHeader">
        <Button href="/" variant="primary">
          Return to Meme Gallery
        </Button>
        <h1>Login</h1>
        <Form onSubmit={handleSubmit}>
          <InputGroup>
            <InputGroup.Prepend>
              <InputGroup.Text>Username</InputGroup.Text>
            </InputGroup.Prepend>
            <FormControl
              type="text"
              name="username"
              placeholder="Username..."
              onChange={handleChange}
              value={values.username}
            />
          </InputGroup>
          <p style={{ color: "red" }}>
            {errors.username ? errors.username : null}
          </p>
          <InputGroup>
            <InputGroup.Prepend>
              <InputGroup.Text>Password</InputGroup.Text>
            </InputGroup.Prepend>
            <FormControl
              type="password"
              name="password"
              placeholder="Password..."
              onChange={handleChange}
              value={values.password}
            />
          </InputGroup>
          <p style={{ color: "red" }}>
            {errors.password ? errors.password : null}
          </p>
          <Button type="submit" variant="primary">
            Login
          </Button>
          <br></br>
          Need to make an account? <a href="/sign-up">Sign Up Here</a>
        </Form>
      </header>
    </div>
  );
}

export default Login;
