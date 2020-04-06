import React from 'react';
import './SignUp.css';
import axios from 'axios';
import { useFormik } from 'formik';
import * as Yup from 'yup';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';
import FormControl from 'react-bootstrap/FormControl';
import { useHistory  } from 'react-router-dom';

const validationSchema = Yup.object({
    username: Yup.string().email().max(30).required('Required'),
    password: Yup.string().min(8).max(50).matches(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*])(?=.{8,})/,
    'Password must have 8 characters, 1 lowercase, 1 uppercase, 1 number, and 1 special character').required('Required'),
    confirmPassword: Yup.string().required('Required').oneOf([Yup.ref('password'), null], 'Passwords must match')
});

function SignUp() {

    const history = useHistory();

    const { handleSubmit, handleChange, values, errors } = useFormik({
        initialValues: {
            username: '',
            password: '',
            confirmPassword: ''
        },
        validationSchema,
        onSubmit(values) {
            axios.post('http://localhost:8080/users/sign-up', ({
                username: values.username,
                password: values.password
            })).then(res => {
                if( res.data === "User has been created") {
                    history.push(`/`);
                }
                else {
                    alert("User already exists.  Please select a different username");
                }
            })
        }
    });

    return(
        <div className='signup'>
            <header className='signupHeader'>
                <Button href='/' variant='primary'>Return to Meme Creation</Button>
                <h1>Sign Up for an Abstract Memery Account</h1>
                <Form onSubmit={handleSubmit}>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text>Username</InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl 
                            type='text'
                            name='username'
                            placeholder="Username..."
                            onChange={handleChange}
                            value={values.username}
                        />
                    </InputGroup>
                    <p style={{ color: 'red' }}>{errors.username ? errors.username : null}</p>
                    <br></br>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text>Password</InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl 
                            type='password'
                            name='password'
                            placeholder="Password..."
                            onChange={handleChange}
                            value={values.password}
                        />
                    </InputGroup>
                    <p style={{ color: 'red' }}>{errors.password ? errors.password : null}</p>
                    <br></br>
                    <InputGroup>
                        <InputGroup.Prepend>
                            <InputGroup.Text>Confirm Password</InputGroup.Text>
                        </InputGroup.Prepend>
                        <FormControl 
                            type='password'
                            name='confirmPassword'
                            placeholder="Confirm Password..."
                            onChange={handleChange}
                            value={values.confirmPassword}
                        />
                    </InputGroup>
                    <p style={{ color: 'red' }}>{errors.confirmPassword ? errors.confirmPassword : null}</p>
                    <br></br>
                    <Button type='submit' variant='primary'>Sign Up</Button>
                    <br></br>
                    Already Have An Account? {' '}
                    <a href='/login'>Login Here</a>
                </Form>
            </header>
        </div>
    );
}

export default SignUp;